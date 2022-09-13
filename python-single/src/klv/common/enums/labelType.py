from enum import IntEnum


class LabelType(IntEnum):
  NONE = 0  # No text set, usually empty string. This is the default when creating empty entries.
  NATIVE = 1  # A Native label, usually a port name
  PORT_USER = 2  # A port user label
  FADER_USER = 3  # A fader or buss user label
  TONE = 4  # Sent for paths showing Tone
  OTHER = 5  # Anything that doesn't fit !
  WARNING = 6  # Warning, like locked or isolated
  LEVEL = 7  # Level
  FORMAT = 8  # Path format (M, ST, 5.1, etc,...)
  PFL = 9  # PFL Indicator
  MASTER = 10  # VCA Master
  LAYER = 11 # The fader layer
  SUBLAYER = 12  # The sublayer
  FADER_NUMBER = 13  # Fader number, eg S10 L3
  INSERT = 14  #ALIAS,  #LINE1_TEXT,  # Force display of line 1 (eg debug text: cli disp dsp "1.6")
  LINE2_TEXT = 15  # Force display of line 2 (eg debug text: cli disp dsp "DSP Legs")
  HPO_REAL_SOURCE = 16  # Hydra Patchbay real source label
  INHERITED = 17  # SW-P-08 label
  LOCAL_LIST = 18  # The Apollo/Artemis list label
  REMOTE_FADER_LABEL = 19  # A remote fader label
  TRANSMITTER_LABEL = 20  # A transmitter label provided by the router*/
  SPILLED_FADER_LABEL = 21  # The label to be shown on Ivor spilled faders
