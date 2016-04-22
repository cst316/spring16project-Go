package net.sf.memoranda.ui.timer;



import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.sound.midi.*;

public class SoundEngine{
	
	private static Sequencer	sequencer;
	private static Synthesizer	synthesizer;
	
	public void playSound(){
	  Sequence sequence = null;
	  File midiFile = new File("src/net/sf/memoranda/ui/timer/nokia_tune.mid");
	  //URL midiFile = getClass().getResource("nokia_tune.mid");

		/*
		 *	We read in the MIDI file to a Sequence object.
		 *	This object is set at the Sequencer later.
		 */
		
		try
		{
			sequence = MidiSystem.getSequence(midiFile);
		}
		catch (InvalidMidiDataException e)
		{
			/*
			 *	In case of an exception, we dump the exception
			 *	including the stack trace to the console.
			 *	Then, we exit the program.
			 */
			e.printStackTrace();
		}
		catch (IOException e)
		{
			/*
			 *	In case of an exception, we dump the exception
			 *	including the stack trace to the console.
			 *	Then, we exit the program.
			 */
			e.printStackTrace();
		}

		/*
		 *	Now, we need a Sequencer to play the sequence.
		 *	Here, we simply request the default sequencer.
		 */
		try
		{
			sequencer = MidiSystem.getSequencer();
		}
		catch (MidiUnavailableException e)
		{
			e.printStackTrace();
		}
		if (sequencer == null)
		{
			System.out.println("SimpleMidiPlayer.main(): can't get a Sequencer");
		}

		/*
		 *	There is a bug in the Sun jdk1.3/1.4.
		 *	It prevents correct termination of the VM.
		 *	So we have to exit ourselves.
		 *	To accomplish this, we register a Listener to the Sequencer.
		 *	It is called when there are "meta" events. Meta event
		 *	47 is end of track.
		 *
		 *	Thanks to Espen Riskedal for finding this trick.
		 */
		sequencer.addMetaEventListener(new MetaEventListener()
			{
				public void meta(MetaMessage event)
				{
					if (event.getType() == 47)
					{
						sequencer.close();
						if (synthesizer != null)
						{
							synthesizer.close();
						}
					}
				}
			});

		/*
		 *	The Sequencer is still a dead object.
		 *	We have to open() it to become live.
		 *	This is necessary to allocate some resources in
		 *	the native part.
		 */
		try
		{
			sequencer.open();
		}
		catch (MidiUnavailableException e)
		{
			e.printStackTrace();
		}

		/*
		 *	Next step is to tell the Sequencer which
		 *	Sequence it has to play. In this case, we
		 *	set it as the Sequence object created above.
		 */
		try
		{
			sequencer.setSequence(sequence);
		}
		catch (InvalidMidiDataException e)
		{
			e.printStackTrace();
		}

		
		if (! (sequencer instanceof Synthesizer))
		{
			/*
			 *	We try to get the default synthesizer, open()
			 *	it and chain it to the sequencer with a
			 *	Transmitter-Receiver pair.
			 */
			try
			{
				synthesizer = MidiSystem.getSynthesizer();
				synthesizer.open();
				Receiver	synthReceiver = synthesizer.getReceiver();
				Transmitter	seqTransmitter = sequencer.getTransmitter();
				seqTransmitter.setReceiver(synthReceiver);
			}
			catch (MidiUnavailableException e)
			{
				e.printStackTrace();
			}
		}

		
		sequencer.start();
		System.out.println("Played Alarm");
	}



	
}
