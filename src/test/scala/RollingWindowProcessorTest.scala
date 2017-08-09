import org.scalactic.TolerantNumerics
import org.scalatest.{FlatSpec, Matchers}


class RollingWindowProcessorTest extends FlatSpec with Matchers {
  private implicit val doubleEq = TolerantNumerics.tolerantDoubleEquality(0.00001)

  "RollingWindowProcessor" should "calculate rolling window values" in {
    val inputPath = "src/test/resources/data_small.txt"
    val resultPath = "src/test/resources/data_small_result.txt"

    val rws = RollingWindowProcessor.getRollingWindows(inputPath).toSeq
    val results = Data.fromFile(resultPath).toSeq

    rws.size should be(results.size)

    rws.zip(results).foreach { case (rw, res) =>
      rw.lastTs should equal(res.t)
      rw.lastValue should equal(res.v)
      rw.size should equal(res.n)
      rw.sum should equal(res.rs)
      rw.min should equal(res.minV)
      rw.max should equal(res.maxV)
    }
  }
}