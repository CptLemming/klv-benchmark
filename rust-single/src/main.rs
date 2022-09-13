use std::time::{Instant, Duration};

pub mod gql;
pub mod klv;

use bytes::{Bytes, Buf};
use gql::meters::meters::Meters;
use klv::commands::meterDisplayBlockUpdate::MeterDisplayBlockUpdate;

struct Record {
    index: u32,
    duration: Duration,
}

fn main() {
    let start = Instant::now();
    let mut records = vec![];
    let num_runs = 100000;

    for index in 0..num_runs {
        let start = Instant::now();
        make_meter_blocks();
        records.push(Record { index, duration: Instant::elapsed(&start) });
    }

    let end = Instant::elapsed(&start);

    println!("Elapsed :: {}", end.as_micros());
    println!("Runs    :: {}", num_runs);
    println!("Avg     :: {}", records.iter().map(|r| r.duration.as_micros()).sum::<u128>() as f64 / records.len() as f64);
}

fn make_meter_blocks() {
    let msg = vec![13, 0, 0, 40, 252, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 28, 1, 2, 0, 6, 0, 0, 0, 77, 97, 105, 110, 32, 49, 1, 4, 0, 0, 0, 76, 105, 110, 101, 1, 0, 0, 0, 0, 0, 6, 0, 0, 0, 128, 11, 129, 11, 130, 11, 131, 11, 132, 11, 133, 11, 0, 0, 0, 0, 6, 0, 0, 0, 1, 0, 0, 0, 76, 1, 0, 0, 0, 82, 1, 0, 0, 0, 67, 1, 0, 0, 0, 101, 1, 0, 0, 0, 108, 1, 0, 0, 0, 114, 8, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 255, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 128, 2, 0, 255, 255, 0, 0, 0, 0, 0, 0, 0, 0, 7, 0, 2, 28, 1, 2, 0, 6, 0, 0, 0, 77, 97, 105, 110, 32, 50, 1, 4, 0, 0, 0, 76, 105, 110, 101, 1, 0, 0, 0, 0, 0, 6, 0, 0, 0, 134, 11, 135, 11, 136, 11, 137, 11, 138, 11, 139, 11, 0, 0, 0, 0, 6, 0, 0, 0, 1, 0, 0, 0, 76, 1, 0, 0, 0, 82, 1, 0, 0, 0, 67, 1, 0, 0, 0, 101, 1, 0, 0, 0, 108, 1, 0, 0, 0, 114, 8, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 255, 0, 0, 1, 0, 3, 0, 0, 0, 0, 0, 128, 2, 0, 255, 255, 0, 0, 0, 0, 0, 0, 0, 0, 7, 1];
    let mut buf = Bytes::from(msg);

    // Header
    let val = buf.get_u32_le();
    let _klv_type = (val << 0) >> 27;
    let _version = (val << 5) >> 22;
    let _id = (val << 10) >> 10;
    let _len = buf.get_u32_le();

    // println!("header :: {}", val);
    // println!("_klv_type :: {}", _klv_type);
    // println!("_version :: {}", _version);
    // println!("_id :: {}", _id);
    // println!("_len :: {}", _len);

    let meter_display_block_update = MeterDisplayBlockUpdate::read(&mut buf);

    let _meters: Meters = meter_display_block_update.into();
}
