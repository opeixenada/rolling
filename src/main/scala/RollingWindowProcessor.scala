import java.io.File

import model.{Item, RollingWindow}

import scala.io.Source


object RollingWindowProcessor {

  private val T = 60

  def main(args: Array[String]): Unit = {
    val path = args(0)
    val windows = getRollingWindows(path)
    Printer.print(windows)
  }

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