package nz.ac.auckland.se206.speech;

import javax.speech.AudioException;
import javax.speech.Central;
import javax.speech.EngineException;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;

/** Text-to-speech API using the JavaX speech library. */
public class TextToSpeech {
  /** Custom unchecked exception for Text-to-speech issues. */
  static class TextToSpeechException extends RuntimeException {
    public TextToSpeechException(final String message) {
      super(message);
    }
  }

  private final Synthesizer synthesizer;
  private static boolean isMuted = false;

  /**
   * Constructs the TextToSpeech object creating and allocating the speech synthesizer. English
   * voice: com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory
   */
  public TextToSpeech() {
    try {
      System.setProperty(
          "freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
      Central.registerEngineCentral("com.sun.speech.freetts.jsapi.FreeTTSEngineCentral");

      synthesizer = Central.createSynthesizer(new SynthesizerModeDesc(java.util.Locale.ENGLISH));

      synthesizer.allocate();
    } catch (final EngineException e) {
      throw new TextToSpeechException(e.getMessage());
    }
  }

  /** Mute or unmute the text-to-speech output. */
  public static void setMute() {
    if (isMuted == true) {
      isMuted = false;
      System.out.println("Unmuted");
    } else {
      isMuted = true;
      System.out.println("Muted");
    }
  }

  // Get Mute status

  public static boolean getMute() {
    return isMuted;
  }

  /**
   * Speaks the given list of sentences.
   *
   * @param sentences A sequence of strings to speak.
   */
  public void speak(final String... sentences) {
    boolean isFirst = true;

    for (final String sentence : sentences) {
      if (isFirst) {
        isFirst = false;
      } else {
        // Add a pause between sentences.
        sleep();
      }

      speak(sentence);
    }
  }

  /**
   * Speaks the given sentence in input.
   *
   * @param sentence A string to speak.
   */
  public void speak(final String sentence) {
    if (sentence == null) {
      throw new IllegalArgumentException("Text cannot be null.");
    }

    if (!isMuted) {
      try {
        synthesizer.resume();
        synthesizer.speakPlainText(sentence, null);
        synthesizer.waitEngineState(Synthesizer.QUEUE_EMPTY);
      } catch (final AudioException | InterruptedException e) {
        throw new TextToSpeechException(e.getMessage());
      }
    }
  }

  /** Sleeps a while to add some pause between sentences. */
  private void sleep() {
    try {
      Thread.sleep(100);
    } catch (final InterruptedException e) {
      e.printStackTrace();
    }
  }

  /**
   * It deallocates the speech synthesizer. If you are experiencing an IllegalThreadStateException,
   * avoid using this method and run the speak method without terminating.
   */
  public void terminate() {
    try {
      synthesizer.deallocate();
    } catch (final EngineException e) {
      throw new TextToSpeechException(e.getMessage());
    }
  }
}
