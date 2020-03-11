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

import zio.Has

/**
 * An `Invocation[R, I, E, A]` models a single invocation of a `Method[R, I, A]`,
 * including both the input to the method invocation `I` and the failure `E` or
 * success value `A` of the method invocation.
 */
final case class Invocation[R <: Has[_], I, E, A](method: Method[R, I, E, A], input: I, output: Either[E, A])
