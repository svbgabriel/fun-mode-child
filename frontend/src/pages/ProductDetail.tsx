import React from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { useQuery } from '@tanstack/react-query';
import api from '../services/api';
import { Product } from '../types';
import { ShoppingCart, Heart, ArrowLeft, ShieldCheck, Truck, RefreshCcw } from 'lucide-react';
import { useCartStore } from '../store/useCartStore';

const ProductDetail: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const { addItem } = useCartStore();

  const { data: product, isLoading, isError } = useQuery<Product>({
    queryKey: ['product', id],
    queryFn: async () => {
      const response = await api.get(`/products/${id}`);
      return response.data;
    },
    enabled: !!id,
  });

  const formatPrice = (price: number) => {
    return new Intl.NumberFormat('pt-BR', {
      style: 'currency',
      currency: 'BRL',
    }).format(price);
  };

  if (isLoading) {
    return (
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-20">
        <div className="animate-pulse flex flex-col md:flex-row gap-12">
          <div className="w-full md:w-1/2 aspect-square bg-gray-200 rounded-3xl"></div>
          <div className="w-full md:w-1/2 space-y-6">
            <div className="h-4 bg-gray-200 rounded w-1/4"></div>
            <div className="h-10 bg-gray-200 rounded w-3/4"></div>
            <div className="h-6 bg-gray-200 rounded w-1/2"></div>
            <div className="h-32 bg-gray-200 rounded"></div>
            <div className="h-12 bg-gray-200 rounded w-full"></div>
          </div>
        </div>
      </div>
    );
  }

  if (isError || !product) {
    return (
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-20 text-center">
        <h2 className="text-3xl font-bold text-gray-900 mb-4">Produto não encontrado</h2>
        <button 
          onClick={() => navigate('/products')}
          className="bg-primary-600 text-white px-8 py-3 rounded-full font-bold hover:bg-primary-700 transition"
        >
          Voltar para produtos
        </button>
      </div>
    );
  }

  return (
    <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-12">
      <button 
        onClick={() => navigate(-1)}
        className="flex items-center text-gray-500 hover:text-primary-600 font-bold mb-8 transition"
      >
        <ArrowLeft className="w-5 h-5 mr-2" />
        Voltar
      </button>

      <div className="flex flex-col md:flex-row gap-12">
        {/* Product Image */}
        <div className="w-full md:w-1/2">
          <div className="relative aspect-square rounded-3xl overflow-hidden bg-gray-50 border border-gray-100 shadow-sm">
            <img 
              src={product.referenceBig ? `/api${product.referenceBig}` : (product.reference ? `/api${product.reference}` : 'https://images.unsplash.com/photo-1515488042361-ee00e0ddd4e4?auto=format&fit=crop&q=80&w=1475')} 
              alt={product.name} 
              className="w-full h-full object-cover"
              onError={(e) => {
                (e.target as HTMLImageElement).src = 'https://images.unsplash.com/photo-1515488042361-ee00e0ddd4e4?auto=format&fit=crop&q=80&w=1475';
              }}
            />
            <button className="absolute top-6 right-6 p-3 bg-white/80 backdrop-blur-sm rounded-full text-gray-500 hover:text-red-500 shadow-md transition">
              <Heart className="w-6 h-6" />
            </button>
          </div>
        </div>

        {/* Product Info */}
        <div className="w-full md:w-1/2 space-y-8">
          <div>
            <span className="inline-block px-4 py-1 rounded-full bg-primary-100 text-primary-600 text-sm font-bold uppercase tracking-wider mb-4">
              {product.category.name}
            </span>
            <h1 className="text-4xl font-black text-gray-900 mb-4 leading-tight">
              {product.name}
            </h1>
            <p className="text-lg text-gray-600 leading-relaxed">
              {product.description}
            </p>
          </div>

          <div className="bg-gray-50 p-8 rounded-3xl border border-gray-100">
            <div className="flex items-end gap-4 mb-2">
              <span className="text-4xl font-black text-gray-900">
                {formatPrice(product.price)}
              </span>
              <span className="text-gray-400 font-bold line-through mb-1">
                {formatPrice(product.price * 1.2)}
              </span>
            </div>
            <p className="text-primary-600 font-bold mb-8">
              Em até 10x de {formatPrice(product.price / 10)} sem juros
            </p>

            <div className="flex flex-col sm:flex-row gap-4">
              <button 
                onClick={() => addItem(product)}
                className="flex-grow bg-primary-600 text-white py-4 px-8 rounded-2xl font-bold text-lg hover:bg-primary-700 transition flex items-center justify-center gap-3 shadow-xl shadow-primary-200"
              >
                <ShoppingCart className="w-6 h-6" />
                Adicionar ao Carrinho
              </button>
            </div>
          </div>

          {/* Guarantees */}
          <div className="grid grid-cols-1 sm:grid-cols-3 gap-6 pt-4">
            <div className="flex items-center gap-3 text-gray-600">
              <Truck className="w-6 h-6 text-primary-500" />
              <span className="text-xs font-bold uppercase tracking-tight">Frete Grátis</span>
            </div>
            <div className="flex items-center gap-3 text-gray-600">
              <ShieldCheck className="w-6 h-6 text-primary-500" />
              <span className="text-xs font-bold uppercase tracking-tight">Garantia 1 ano</span>
            </div>
            <div className="flex items-center gap-3 text-gray-600">
              <RefreshCcw className="w-6 h-6 text-primary-500" />
              <span className="text-xs font-bold uppercase tracking-tight">Troca Fácil</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ProductDetail;
