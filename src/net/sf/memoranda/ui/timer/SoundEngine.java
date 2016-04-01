package net.sf.memoranda.ui.timer;


import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequencer;

public class SoundEngine
{
  public void playSound()
  {
    InputStream is = getClass().getClassLoader().getResourceAsStream("nokia_tune.mid");
    try
    {
      Sequencer sequencer = MidiSystem.getSequencer();
      sequencer.setSequence(MidiSystem.getSequence(is));
      sequencer.open();
      sequencer.start();
      while (sequencer.isRunning()) {
        try
        {
          Thread.sleep(1000L);
        }
        catch (InterruptedException localInterruptedException) {}
      }
      sequencer.stop();
      sequencer.close();
    }
    catch (MidiUnavailableException mue)
    {
      System.out.println("Midi device unavailable!");
    }
    catch (IOException ioe)
    {
      System.out.println("IO Error!");
    }
    catch (InvalidMidiDataException e)
    {
      System.out.println("Invalid Midi data!");
    }
  }
}
