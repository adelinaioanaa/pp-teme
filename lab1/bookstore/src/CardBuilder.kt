/*
 * Copyright (c) 2018 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

import org.w3c.dom.*
import kotlin.browser.document
import kotlin.browser.window
import kotlin.dom.addClass

class CardBuilder {

  fun build(book: Book): Element {
    val containerElement = document.createElement("div") as HTMLDivElement
    val imageElement = document.createElement("img") as HTMLImageElement
    val titleElement = document.createElement("div") as HTMLDivElement
    val priceElement = document.createElement("div") as HTMLDivElement
    val descriptionElement = document.createElement("div") as HTMLDivElement
    val viewDetailsButtonElement = document.createElement("button") as HTMLButtonElement

    // bind data
    bind(book = book,
        imageElement = imageElement,
        titleElement = titleElement,
        priceElement = priceElement,
        descriptionElement = descriptionElement,
        viewDetailsButtonElement = viewDetailsButtonElement)

    // apply styles
    applyStyle(containerElement,
        imageElement = imageElement,
        titleElement = titleElement,
        priceElement = priceElement,
        descriptionElement = descriptionElement,
        viewDetailsButtonElement = viewDetailsButtonElement)

    containerElement
        .appendChild(
            imageElement,
            titleElement,
            descriptionElement,
            priceElement,
            viewDetailsButtonElement
        )
    return containerElement
  }

  // Apply CSS Classes
  private fun applyStyle(containerElement: HTMLDivElement,
                         imageElement: HTMLImageElement,
                         titleElement: HTMLDivElement,
                         priceElement: HTMLDivElement,
                         descriptionElement: HTMLDivElement,
                         viewDetailsButtonElement: HTMLButtonElement) {

    containerElement.addClass("card", "card-shadow")
    imageElement.addClass("cover-image")
    titleElement.addClass("text-title", " float-left")
    descriptionElement.addClass("text-description", " float-left")
    priceElement.addClass("text-price", " float-left")
    viewDetailsButtonElement.addClass("view-details", "ripple", "float-right")
  }

  // Bind data to the view
  private fun bind(book: Book,
                   imageElement: HTMLImageElement,
                   titleElement: HTMLDivElement,
                   priceElement: HTMLDivElement,
                   descriptionElement: HTMLDivElement,
                   viewDetailsButtonElement: HTMLButtonElement) {

    imageElement.src = book.coverUrl

    titleElement.innerHTML = book.title
    priceElement.innerHTML = book.price
    descriptionElement.innerHTML = book.description
    viewDetailsButtonElement.innerHTML = "view details"

	viewDetailsButtonElement.addEventListener("click", {
      window.open(book.url)
    })
  }

  private fun Element.appendChild(vararg elements: Element) {
    elements.forEach {
      this.appendChild(it)
    }
  }
}
