package devanmejia.todolist.exception

class NotPermittedException(message: String)
    : IllegalArgumentException("Not permit: $message") {
}
