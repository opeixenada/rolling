package model


case class RollingWindow(values: Seq[Item] = Seq.empty) {
  lazy val lastTs: Long = values.head.ts
  lazy val lastValue: Double = values.head.value
  lazy val size: Int = values.size
  lazy val sum: Double = values.map(_.value).sum
  lazy val min: Double = values.map(_.value).min
  lazy val max: Double = values.map(_.value).max
}