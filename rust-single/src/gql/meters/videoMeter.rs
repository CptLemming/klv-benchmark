use crate::klv::common::enums::videoSource::VideoSource;


#[derive(Debug, Clone)]
pub struct VideoMeter {
  pub source: VideoSource,
}
