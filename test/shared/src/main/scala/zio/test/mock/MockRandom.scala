/*
 * Copyright 2017-2020 John A. De Goes and the ZIO Contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package zio.test.mock

import zio.random.Random
import zio.{ Chunk, Has, UIO, URLayer, ZLayer }

object MockRandom {

  sealed trait Tag[I, E, A] extends Method[Random, I, E, A] {
    def envBuilder = MockRandom.envBuilder
  }

  object NextBoolean  extends Tag[Unit, Nothing, Boolean]
  object NextBytes    extends Tag[Int, Nothing, Chunk[Byte]]
  object NextDouble   extends Tag[Unit, Nothing, Double]
  object NextFloat    extends Tag[Unit, Nothing, Float]
  object NextGaussian extends Tag[Unit, Nothing, Double]
  object NextInt {
    object _0 extends Tag[Int, Nothing, Int]
    object _1 extends Tag[Unit, Nothing, Int]
  }
  object NextLong {
    object _0 extends Tag[Unit, Nothing, Long]
    object _1 extends Tag[Long, Nothing, Long]
  }
  object NextPrintableChar extends Tag[Unit, Nothing, Char]
  object NextString        extends Tag[Int, Nothing, String]
  object Shuffle           extends Tag[List[Any], Nothing, List[Any]]

  private lazy val envBuilder: URLayer[Has[Proxy], Random] =
    ZLayer.fromService(invoke =>
      new Random.Service {
        val nextBoolean: UIO[Boolean]                = invoke(NextBoolean)
        def nextBytes(length: Int): UIO[Chunk[Byte]] = invoke(NextBytes, length)
        val nextDouble: UIO[Double]                  = invoke(NextDouble)
        val nextFloat: UIO[Float]                    = invoke(NextFloat)
        val nextGaussian: UIO[Double]                = invoke(NextGaussian)
        def nextInt(n: Int): UIO[Int]                = invoke(NextInt._0, n)
        val nextInt: UIO[Int]                        = invoke(NextInt._1)
        val nextLong: UIO[Long]                      = invoke(NextLong._0)
        def nextLong(n: Long): UIO[Long]             = invoke(NextLong._1, n)
        val nextPrintableChar: UIO[Char]             = invoke(NextPrintableChar)
        def nextString(length: Int)                  = invoke(NextString, length)
        def shuffle[A](list: List[A]): UIO[List[A]]  = invoke(Shuffle, list).asInstanceOf[UIO[List[A]]]
      }
    )
}
