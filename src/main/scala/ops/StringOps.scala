package ops

object StringOps {
  implicit class StringOps(value: String) {
    val consoleRed: String =  Console.RED + value + Console.RESET
    val consoleGreen: String = Console.GREEN + value + Console.RESET
    val consoleBlue: String = Console.YELLOW + value + Console.RESET
  }
}
