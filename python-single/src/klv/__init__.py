class Walker:

  def __init__(self, data):
    self.index = 0
    self.data = data
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

  def read_u8(self):
    val = self.read_bytes(1)
    self.index += 1
    return val

  def read_u16(self):
    val = self.read_bytes(2)
    self.index += 2
    return val

  def read_u32(self):
    val = self.read_bytes(4)
    self.index += 4
    return val

  def read_str(self):
    str_len = self.read_bytes(4)
    self.index += 4
    str_bytes = self.data[self.index:self.index + str_len]

    # Shift negative numbers
    for i, b in enumerate(str_bytes):
        if b < 0:
            str_bytes[i] = 256 + b

    self.index += str_len
    return bytearray(str_bytes).decode('utf-8')
