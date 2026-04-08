package com.adam.evaluation.core.audio

/**
 * Cues audio partagés pour garder une API stable côté présentation.
 */
enum class AudioCue {
    OpenDetail
}

/**
 * Contrat audio utilisé par la couche présentation.
 */
interface AudioManager {
    /**
     * Joue un son associé à un [AudioCue].
     * Retourne un [Result] pour éviter de bloquer le flux UI en cas d'échec audio.
     */
    fun play(cue: AudioCue): Result<Unit>
}

