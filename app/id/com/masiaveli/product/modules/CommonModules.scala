package id.com.masiaveli.product.modules

import com.google.inject.AbstractModule
import id.com.masiaveli.product.repositories.{ProductRepo, ProductRepoImpl}

class CommonModules extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[ProductRepo]).to(classOf[ProductRepoImpl ])
  }
}
