package nova.defense.validate.password

data class PasswordValidationState (
    val hasMin: Boolean = false,
    val hasUpper: Boolean = false,
    val hasSpecial: Boolean = false,
    val successful: Boolean = false,
)
