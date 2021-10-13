package devanmejia.todolist.exception

class NotPermittedException(message: String)
    : IllegalArgumentException("Not permit: $message")

class AuthorizationException(message: String)
    : IllegalArgumentException("Not auth: $message")
