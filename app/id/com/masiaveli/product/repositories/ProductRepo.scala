package id.com.masiaveli.product.repositories

import id.com.masiaveli.product.model.Product
import reactivemongo.api.commands.WriteResult

import scala.concurrent.Future

trait ProductRepo {
  def create(document: Product): Future[WriteResult]
  def list(status: String): Future[List[Product]]
  def get(id: String): Future[Option[Product]]
}
