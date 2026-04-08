package com.adam.evaluation.core.audio

import java.awt.Toolkit

actual class PlatformAudioManager : AudioManager {
    override fun play(cue: AudioCue): Result<Unit> {
        return runCatching {
            when (cue) {
                AudioCue.OpenDetail -> Toolkit.getDefaultToolkit().beep()
            }
        }
    }
}

