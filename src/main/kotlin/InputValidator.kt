object InputValidator {
    fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,3})"
        return !email.isNullOrBlank() && email.matches(emailRegex.toRegex())
    }

    fun isValidPassword(password: String): Boolean {
        return !password.isNullOrBlank() && password.length >= 8
    }

    fun isValidName(name: String): Boolean {
        return name.isNotBlank()
    }

    fun isValidPhoneNumber(phoneNumber: String): Boolean {
        val phoneNumberPattern = Regex("^8\\d{10}$")
        return phoneNumberPattern.matches(phoneNumber)
    }

    fun doPasswordsMatch(password1: String, password2: String): Boolean {
        return !password1.isNullOrBlank() && password1 == password2
    }

}
