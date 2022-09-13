#![allow(non_camel_case_types)]

#[derive(Debug, PartialEq, Copy, Clone)]
pub enum LabelType {
  NONE, // No text set, usually empty string. This is the default when creating empty entries.
  NATIVE, // A Native label, usually a port name
  PORT_USER, // A port user label
  FADER_USER, // A fader or buss user label
  TONE, // Sent for paths showing Tone
  OTHER, // Anything that doesn't fit !
  WARNING, // Warning, like locked or isolated
  LEVEL, // Level
  FORMAT, // Path format (M, ST, 5.1, etc,...)
  PFL, // PFL Indicator
  MASTER, // VCA Master
  LAYER, // The fader layer
  SUBLAYER, // The sublayer
  FADER_NUMBER, // Fader number, eg S10 L3
  INSERT,
  ALIAS,
  LINE1_TEXT, // Force display of line 1 (eg debug text: cli disp dsp "1.6")
  LINE2_TEXT, // Force display of line 2 (eg debug text: cli disp dsp "DSP Legs")
  HPO_REAL_SOURCE, // Hydra Patchbay real source label
  INHERITED, // SW-P-08 label
  LOCAL_LIST, // The Apollo/Artemis list label
  REMOTE_FADER_LABEL, // A remote fader label
  TRANSMITTER_LABEL, // A transmitter label provided by the router*/
  SPILLED_FADER_LABEL, // The label to be shown on Ivor spilled faders
}