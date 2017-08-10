import java.io.File

import model.{Item, RollingWindow}

import scala.io.Source


object RollingWindowProcessor {

  /** Rolling window size in seconds */
  private val T = 60

  /**
    * Performs analysis of price ratios. It accepts a path to a file containing time series on a
    * local file system and prints out results to the standard output.
    *
    * @param args path to the input file
    */
  def main(args: Array[String]): Unit = {
    val path = args(0)
    val windows = getRollingWindows(path)
    Printer.print(windows)
  }

  /**
    * Returns rolling windows constructed from the input.
    *
    * @param path path to the input file
    * @return iterator of `RollingWindow`
    */
  def getRollingWindows(path: String): Iterator[RollingWindow] = {
    val file = new File(path)
    val inputItems = parseInput(Source.fromFile(file).getLines())
    aggregate(inputItems)
  }

  private def parseInput(input: Iterator[String]): Iterator[Item] = {
    input.map { line =>
      line.split(Array(' ', '\t')).toList match {
        case (x :: y :: Nil) => Item(x.toLong, y.toDouble)
        case _ => throw new RuntimeException("Can't parse line " + line)
      }
    }
  }

  /**
    * Maps input items to their corresponding rolling windows of items not older than `T` seconds.
    * Function holds the state with the current window that is used inside the mapping procedure.
    *
    * @param items iterator of `Item`
    * @return iterator of `RollingWindow`
    */
  private def aggregate(items: Iterator[Item]): Iterator[RollingWindow] = {
    var currentWindow: RollingWindow = RollingWindow()

    items.map { item =>
      val lastTs = item.ts - T
      val (toLeave, _) = currentWindow.values.span(_.ts > lastTs)
      val newValues = item +: toLeave
      val newWindow = RollingWindow(newValues)
      currentWindow = newWindow
      newWindow
    }
  }
}