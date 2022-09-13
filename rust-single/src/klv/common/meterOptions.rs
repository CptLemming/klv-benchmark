#![allow(non_snake_case, non_camel_case_types)]
use bytes::{Bytes, Buf};

#[derive(Debug, Clone)]
pub struct MeterOptions {
  pub isAudio: bool,
  pub isDynamics: bool,
  pub isLoudness: bool,
  pub isDownmix: bool,
  pub isPhase: bool,
  pub isSurroundDownmix: bool,
  pub isFullImmersive: bool,
  pub isDownmixLoudness: bool,
  pub isDownmixSurroundLoudness: bool,
  pub isDualTrack: bool,
  pub isQuadTrack: bool,
  pub isTrackPairLoudness: bool,
  pub isTrackOddLoudness: bool,
  pub isTrackEvenLoudness: bool,
}

impl MeterOptions {
  pub fn read(buf: &mut Bytes) -> MeterOptions {
    let bitset = buf.get_u32_le();

    let isAudio = bitset >> 31 & 1 != 0;
    let isDynamics = bitset >> 30 & 1 != 0;
    let isLoudness = bitset >> 29 & 1 != 0;
    let isDownmix = bitset >> 28 & 1 != 0;
    let isPhase = bitset >> 27 & 1 != 0;
    let isSurroundDownmix = bitset >> 26 & 1 != 0;
    let isFullImmersive = bitset >> 25 & 1 != 0;
    let isDownmixLoudness = bitset >> 24 & 1 != 0;
    let isDownmixSurroundLoudness = bitset >> 23 & 1 != 0;
    let isDualTrack = bitset >> 22 & 1 != 0;
    let isQuadTrack = bitset >> 21 & 1 != 0;
    let isTrackPairLoudness = bitset >> 20 & 1 != 0;
    let isTrackOddLoudness = bitset >> 19 & 1 != 0;
    let isTrackEvenLoudness = bitset >> 18 & 1 != 0;

    MeterOptions {
      isAudio,
      isDynamics,
      isLoudness,
      isDownmix,
      isPhase,
      isSurroundDownmix,
      isFullImmersive,
      isDownmixLoudness,
      isDownmixSurroundLoudness,
      isDualTrack,
      isQuadTrack,
      isTrackPairLoudness,
      isTrackOddLoudness,
      isTrackEvenLoudness,
    }
  }
}
