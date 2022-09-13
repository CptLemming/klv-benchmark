import datetime
import ctypes
from gql.meters.meters import Meters
from klv import Walker

from klv.commands.meterDisplayBlockUpdate import MeterDisplayBlockUpdate

class Record:
  index = 0
  duration = 0

  def __init__(self, index, duration):
    self.index = index
    self.duration = duration


def main():
  start = get_time()
  records = []
  num_runs = 100000

  for index in range(0, num_runs):
    run_start = get_time()
    make_meter_blocks()
    records.append(Record(index, get_time() - run_start))

  end = get_time() - start

  print("Elapsed :: ", end.microseconds)
  print("Runs    :: ", num_runs)
  print("Avg     :: ", sum(r.duration.microseconds for r in records) / len(records))


def get_time():
  return datetime.datetime.now()

def make_meter_blocks():
  msg = [13, 0, 0, 40, 252, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 28, 1, 2, 0, 6, 0, 0, 0, 77, 97, 105, 110, 32, 49, 1, 4, 0, 0, 0, 76, 105, 110, 101, 1, 0, 0, 0, 0, 0, 6, 0, 0, 0, 128, 11, 129, 11, 130, 11, 131, 11, 132, 11, 133, 11, 0, 0, 0, 0, 6, 0, 0, 0, 1, 0, 0, 0, 76, 1, 0, 0, 0, 82, 1, 0, 0, 0, 67, 1, 0, 0, 0, 101, 1, 0, 0, 0, 108, 1, 0, 0, 0, 114, 8, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 255, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 128, 2, 0, 255, 255, 0, 0, 0, 0, 0, 0, 0, 0, 7, 0, 2, 28, 1, 2, 0, 6, 0, 0, 0, 77, 97, 105, 110, 32, 50, 1, 4, 0, 0, 0, 76, 105, 110, 101, 1, 0, 0, 0, 0, 0, 6, 0, 0, 0, 134, 11, 135, 11, 136, 11, 137, 11, 138, 11, 139, 11, 0, 0, 0, 0, 6, 0, 0, 0, 1, 0, 0, 0, 76, 1, 0, 0, 0, 82, 1, 0, 0, 0, 67, 1, 0, 0, 0, 101, 1, 0, 0, 0, 108, 1, 0, 0, 0, 114, 8, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 255, 0, 0, 1, 0, 3, 0, 0, 0, 0, 0, 128, 2, 0, 255, 255, 0, 0, 0, 0, 0, 0, 0, 0, 7, 1]

  walker = Walker(msg)

  val = walker.read_u32()

  _klv_type = ctypes.c_int(val << 0).value >> 27
  _version = ctypes.c_int(val << 5).value >> 22
  _id = ctypes.c_int(val << 10).value >> 10

  _len = walker.read_u32()

  meter_display_block_update = MeterDisplayBlockUpdate(walker)
  _meters = Meters.parse(meter_display_block_update)


if __name__ == "__main__":
  main()
