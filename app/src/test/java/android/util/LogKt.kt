package android.util

class Log {

    companion object {
        fun d(tag: String, msg: String): Int {
            println("[$tag:INFO]: $msg")
            return 0
        }

        fun w(tag: String, msg: String): Int {
            println("[$tag:WARNING]: $msg")
            return 0
        }

        fun e(tag: String, msg: String): Int {
            println("[$tag:ERROR]: $msg")
            return 0
        }
    }
}