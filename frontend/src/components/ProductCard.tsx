import React from 'react';
import { Product } from '../types';
import { ShoppingCart, Heart } from 'lucide-react';
import { Link } from 'react-router-dom';
import { useCartStore } from '../store/useCartStore';

interface ProductCardProps {
  product: Product;
}

const ProductCard: React.FC<ProductCardProps> = ({ product }) => {
  const { addItem } = useCartStore();

  const formatPrice = (price: number) => {
    return new Intl.NumberFormat('pt-BR', {
      style: 'currency',
      currency: 'BRL',
    }).format(price);
  };

  return (
    <div className="group bg-white rounded-2xl shadow-sm hover:shadow-xl transition-all duration-300 border border-gray-100 overflow-hidden flex flex-col h-full">
      <div className="relative aspect-square overflow-hidden bg-gray-50">
        <Link to={`/products/${product.id}`}>
          <img 
            src={product.reference ? `/api${product.reference}` : 'https://images.unsplash.com/photo-1515488042361-ee00e0ddd4e4?auto=format&fit=crop&q=80&w=1475'} 
            alt={product.name} 
            className="w-full h-full object-cover group-hover:scale-110 transition-transform duration-500"
            onError={(e) => {
              (e.target as HTMLImageElement).src = 'https://images.unsplash.com/photo-1515488042361-ee00e0ddd4e4?auto=format&fit=crop&q=80&w=1475';
            }}
          />
        </Link>
        <button className="absolute top-4 right-4 p-2 bg-white/80 backdrop-blur-sm rounded-full text-gray-500 hover:text-red-500 shadow-sm transition">
          <Heart className="w-5 h-5" />
        </button>
        {product.price > 100 && (
          <div className="absolute top-4 left-4 bg-primary-600 text-white px-3 py-1 rounded-full text-xs font-bold shadow-lg">
            Destaque
          </div>
        )}
      </div>
      <div className="p-5 flex flex-col flex-grow">
        <div className="mb-2">
          <Link to={`/categories/${product.category.id}`} className="text-xs font-bold text-primary-600 uppercase tracking-wider hover:underline">
            {product.category.name}
          </Link>
        </div>
        <Link to={`/products/${product.id}`} className="mb-3 block">
          <h3 className="text-lg font-bold text-gray-900 group-hover:text-primary-600 transition truncate">
            {product.name}
          </h3>
          <p className="text-gray-500 text-sm line-clamp-2 mt-1">
            {product.description}
          </p>
        </Link>
        <div className="mt-auto flex items-center justify-between">
          <div className="flex flex-col">
            <span className="text-2xl font-black text-gray-900">
              {formatPrice(product.price)}
            </span>
            <span className="text-[10px] text-gray-400 font-bold uppercase">
              10x de {formatPrice(product.price / 10)}
            </span>
          </div>
          <button 
            onClick={() => addItem(product)}
            className="bg-primary-600 text-white p-3 rounded-xl hover:bg-primary-700 transition-colors shadow-lg shadow-primary-200"
          >
            <ShoppingCart className="w-6 h-6" />
          </button>
        </div>
      </div>
    </div>
  );
};

export default ProductCard;
