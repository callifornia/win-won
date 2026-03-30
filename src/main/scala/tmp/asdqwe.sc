val topics = 15
val days = 7


val list = 1 :: 2 :: 3 :: 4 :: 5 :: 6 :: 7 :: Nil

val n = Math.ceil(topics.toDouble / days.toDouble).toInt

val result =
  for {
    n <- 1 to n
  } yield list ::: list


val result = (1 to n).foldLeft(List.empty[Int]) { (acc, _) =>
  acc ::: list
}



result.take(topics).size
