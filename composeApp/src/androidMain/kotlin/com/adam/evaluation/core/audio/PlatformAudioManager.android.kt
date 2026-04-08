package com.adam.evaluation.core.audio

import android.media.AudioManager.STREAM_NOTIFICATION
import android.media.ToneGenerator

actual class PlatformAudioManager : AudioManager {
    override fun play(cue: AudioCue): Result<Unit> {
        return runCatching {
            when (cue) {
                AudioCue.OpenDetail -> {
                    val tone = ToneGenerator(STREAM_NOTIFICATION, 90)
                    try {
                        tone.startTone(ToneGenerator.TONE_PROP_ACK, 120)
                    } finally {
                        tone.release()
                    }
                }
            }
        }
    }
}

