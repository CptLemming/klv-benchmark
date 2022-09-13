import ctypes
from . import fields
from . import models


class Reader:
    def __init__(self, data):
        self.data = data
        self.index = 0
        self.bit_buffer = None

    def read_bytes(self, count, byteorder="little", signed=False):
        assert self.index + \
            count <= len(
                self.data), f"Tried to read too many bytes: {self.index + count} of {len(self.data)}"
        byte_buffer = self.data[self.index:self.index + count]

        # Shift negative numbers
        for i, b in enumerate(byte_buffer):
            if b < 0:
                byte_buffer[i] = 256 + b

        return int.from_bytes(byte_buffer, byteorder=byteorder, signed=signed)

    def read_int(self, field: fields.IntField):
        val = self.read_bytes(field.bytes, field.byteorder, field.signed)

        if field.bits is None:
            self.index += field.bytes

            if field.enum is not None:
                return field.enum(val if val < len(field.enum) else 0)
            return val

        max_bits = field.bytes * 8
        if self.bit_buffer is None:
            self.bit_buffer = max_bits

        assert field.bits <= self.bit_buffer, "Requested more bits than remain in buffer"

        bit_value = ctypes.c_int(
            val << (max_bits - self.bit_buffer)).value >> (max_bits - field.bits)
        self.bit_buffer -= field.bits

        if self.bit_buffer == 0:
            self.bit_buffer = None
            self.index += field.bytes

        if field.enum is not None:
            return field.enum(bit_value if bit_value < len(field.enum) else 0)
        return bit_value

    def read_bool(self, count):
        val = self.read_bytes(4)
        self.index += count
        return val > 0

    def read_string(self):
        str_len = self.read_bytes(4)
        self.index += 4
        str_bytes = self.data[self.index:self.index + str_len]

        # Shift negative numbers
        for i, b in enumerate(str_bytes):
            if b < 0:
                str_bytes[i] = 256 + b

        self.index += str_len
        return bytearray(str_bytes).decode('utf-8')

    def read_list(self, field: fields.ListField):
        list_len = field.fixed_size
        if list_len is None:
            list_len = self.read_bytes(field.bytes)
            self.index += field.bytes
        for i in range(list_len):
            self.read_into(field.model())

    def read_into(self, obj: models.BaseKlvData):
        bases = type.mro(type(obj))
        bases.reverse()
        for base in bases:
            if hasattr(base, "_klv_meta_fields"):
                for k, v in base._klv_meta_fields.items():
                    if isinstance(v, fields.IntField):
                        obj.__dict__[k] = self.read_int(v)
                    elif isinstance(v, fields.BooleanField):
                        obj.__dict__[k] = self.read_bool(v.bytes)
                    elif isinstance(v, fields.StringField):
                        obj.__dict__[k] = self.read_string()
                    elif isinstance(v, fields.ListField):
                        self.read_list(v)
                    elif isinstance(v, models.BaseKlvData):
                        self.read_into(v)
