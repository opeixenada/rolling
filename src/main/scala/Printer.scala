import java.text.DecimalFormat

import model.RollingWindow


/**
  * This object holds functionality to print the rolling window analysis results to the standard
  * output as a table.
  */
object Printer {

  case class Column(name: String, width: Int)

  private val columns = Seq("T" -> 10, "V" -> 7, "N" -> 2, "RS" -> 8, "MinV" -> 7, "MaxV" -> 7)
    .map { case (name, width) => Column(name, width) }

  private val doubleFormat = new DecimalFormat("0.#####")

  def print(input: Iterator[RollingWindow]): Unit = {
    printHeader()

    for (window <- input) {
      val row = mkRow(Seq(
        "%d".format(window.lastTs),
        doubleFormat.format(window.lastValue),
        "%d".format(window.size),
        doubleFormat.format(window.sum),
        doubleFormat.format(window.min),
        doubleFormat.format(window.max)))

      println(row)
    }
  }

  private def printHeader(): Unit = {
    val tableWidth = columns.map(_.width).sum + columns.size - 1
    val header = mkRow(columns.map(_.name))
    val separator = "-" * tableWidth

    println(header)
    println(separator)
  }

  private def formatCell(value: String, size: Int): String = {
    value + " " * (size + 1 - value.length())
  }

  private def mkRow(values: Seq[String]) = values.zip(columns.map(_.width)).map {
    case (value, size) => formatCell(value, size)
  }.mkString

}