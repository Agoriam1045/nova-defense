package nova.defense.validate.password

class ValidatePassword {

    fun execute(password: String): PasswordValidationState {
        val validateSpecialCharacter = validateSpecialCharacter(password)
        val validateCaptalLetter = validateCapitalLetter(password)
        val validateMinimum = validateMinimum(password)

        val hasError = listOf(
            validateMinimum,
            validateCaptalLetter,
            validateSpecialCharacter,
        ).all { it }

        return PasswordValidationState(
            hasMin = validateMinimum,
            hasUpper = validateCaptalLetter,
            hasSpecial = validateSpecialCharacter,
            successful = hasError
        )
    }

    private fun validateSpecialCharacter(password: String): Boolean =
        password.contains(Regex("[^a-zA-Z0-9]"))
    private fun validateCapitalLetter(password: String): Boolean =
        password.contains(Regex(".*[A-Z].*"))
    private fun validateMinimum(password: String): Boolean =
        password.length >= 6
}