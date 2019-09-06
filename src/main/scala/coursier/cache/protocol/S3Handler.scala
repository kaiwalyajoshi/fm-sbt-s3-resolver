/*
 * Copyright 2019 Frugal Mechanic (http://frugalmechanic.com)
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
package coursier.cache.protocol

import fm.sbt.s3.S3URLConnection
import java.net.{URL, URLConnection, URLStreamHandler, URLStreamHandlerFactory}

/**
 * Coursier will look for a coursier.cache.protocol.S3Handler class when it sees the "s3" protocol.
 *
 * This appears to be new (at least to me as of 2019-09-05) so this is being added as an alternative to directly
 * registering the "s3" protocol with java.net.URL.setURLStreamHandlerFactory().
 *
 * Note: This implements both URLStreamHandler and URLStreamHandlerFactory because the Coursier code comments say
 *       it should be a URLStreamHandler but the actual code is casting it to a URLStreamHandlerFactory.  So we will
 *       just implement both in case it ever changes on the Coursier side from one to the other.
 */
final class S3Handler extends URLStreamHandler with URLStreamHandlerFactory {
  println("fm-sbt-s3-resolver - coursier.cache.protocol.S3Handler created")

  // URLStreamHandlerFactory implementation
  def createURLStreamHandler(protocol: String): URLStreamHandler = {
    if (protocol.equalsIgnoreCase("s3")) this
    else null
  }

  // URLStreamHandler implementation
  def openConnection(url: URL): URLConnection = new S3URLConnection(url)
}