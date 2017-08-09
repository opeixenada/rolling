import java.io.File

import org.scalatest.Matchers

import scala.io.Source


object Data extends Matchers {

  case class Result(t: Long, v: Double, n: Int, rs: Double, minV: Double, maxV: Double)

  def fromFile(path: String): Iterator[Result] = {
    val file = new File(path)
    Source.fromFile(file).getLines().map { line =>
      line.split(' ').toList match {
        case (t :: v :: n :: rs :: minV :: maxV :: Nil) =>
          Result(t.toLong, v.toDouble, n.toInt, rs.toDouble, minV.toDouble, maxV.toDouble)
        case _ => throw new RuntimeException("Can't parse line " + line)
      }
    }
  }

}
