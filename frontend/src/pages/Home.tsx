import React from 'react';
import { useQuery } from '@tanstack/react-query';
import api from '../services/api';
import { Product, Category } from '../types';
import ProductCard from '../components/ProductCard';
import { ArrowRight, Star, ShieldCheck, Truck } from 'lucide-react';
import { Link } from 'react-router-dom';

const Home: React.FC = () => {
  const { data: products, isLoading: productsLoading } = useQuery<Product[]>({
    queryKey: ['products'],
    queryFn: async () => {
      const response = await api.get('/products');
      return response.data;
    },
  });

  const { data: categories, isLoading: categoriesLoading } = useQuery<Category[]>({
    queryKey: ['categories'],
    queryFn: async () => {
      const response = await api.get('/categories');
      return response.data;
    },
  });

  return (
    <div className="space-y-12 pb-12">
      {/* Hero Section */}
      <section className="relative bg-primary-600 h-[500px] flex items-center overflow-hidden">
        <div className="absolute inset-0 z-0">
          <div className="absolute inset-0 bg-black opacity-40"></div>
          <img 
            src="/api/assets/img/1.jpg" 
            alt="Toys background" 
            className="w-full h-full object-cover"
            onError={(e) => {
              (e.target as HTMLImageElement).src = 'https://images.unsplash.com/photo-1539627831859-a911cf042f8d?auto=format&fit=crop&q=80&w=2071';
            }}
          />
        </div>
        <div className="relative z-10 max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 text-white">
          <h1 className="text-4xl md:text-6xl font-extrabold mb-4 tracking-tight">
            Diversão sem limites <br /> para seus pequenos!
          </h1>
          <p className="text-xl md:text-2xl mb-8 max-w-2xl text-gray-100">
            Descubra os melhores brinquedos, jogos e acessórios para todas as idades na FunModeChild.
          </p>
          <div className="flex flex-wrap gap-4">
            <Link to="/products" className="bg-white text-primary-600 px-8 py-3 rounded-full font-bold text-lg hover:bg-gray-100 transition shadow-lg">
              Comprar Agora
            </Link>
            <Link to="/categories" className="bg-transparent border-2 border-white text-white px-8 py-3 rounded-full font-bold text-lg hover:bg-white hover:text-primary-600 transition">
              Ver Categorias
            </Link>
          </div>
        </div>
      </section>

      {/* Features Section */}
      <section className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="grid grid-cols-1 md:grid-cols-3 gap-8">
          <div className="flex items-start space-x-4 p-6 bg-white rounded-xl shadow-sm border border-gray-100">
            <div className="bg-primary-100 p-3 rounded-lg text-primary-600">
              <Truck className="w-8 h-8" />
            </div>
            <div>
              <h3 className="text-lg font-bold text-gray-900 mb-1">Entrega Rápida</h3>
              <p className="text-gray-500 text-sm">Enviamos para todo o Brasil com agilidade e segurança.</p>
            </div>
          </div>
          <div className="flex items-start space-x-4 p-6 bg-white rounded-xl shadow-sm border border-gray-100">
            <div className="bg-primary-100 p-3 rounded-lg text-primary-600">
              <ShieldCheck className="w-8 h-8" />
            </div>
            <div>
              <h3 className="text-lg font-bold text-gray-900 mb-1">Compra Segura</h3>
              <p className="text-gray-500 text-sm">Seus dados protegidos com a melhor tecnologia de segurança.</p>
            </div>
          </div>
          <div className="flex items-start space-x-4 p-6 bg-white rounded-xl shadow-sm border border-gray-100">
            <div className="bg-primary-100 p-3 rounded-lg text-primary-600">
              <Star className="w-8 h-8" />
            </div>
            <div>
              <h3 className="text-lg font-bold text-gray-900 mb-1">Qualidade Premium</h3>
              <p className="text-gray-500 text-sm">Trabalhamos apenas com as melhores marcas do mercado.</p>
            </div>
          </div>
        </div>
      </section>

      {/* Categories Highlights */}
      <section className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="flex justify-between items-center mb-8">
          <h2 className="text-3xl font-extrabold text-gray-900">Categorias em Destaque</h2>
          <Link to="/categories" className="text-primary-600 font-bold flex items-center hover:underline">
            Ver todas <ArrowRight className="ml-1 w-4 h-4" />
          </Link>
        </div>
        <div className="grid grid-cols-2 md:grid-cols-4 gap-6">
          {categoriesLoading ? (
            Array.from({ length: 4 }).map((_, i) => (
              <div key={i} className="h-40 bg-gray-200 animate-pulse rounded-xl"></div>
            ))
          ) : (
            categories?.slice(0, 4).map((category) => (
              <Link 
                key={category.id} 
                to={`/categories/${category.id}`}
                className="group relative h-40 rounded-xl overflow-hidden shadow-sm hover:shadow-md transition"
              >
                <div className="absolute inset-0 bg-primary-600 opacity-60 group-hover:opacity-70 transition"></div>
                <div className="absolute inset-0 flex items-center justify-center p-4">
                  <h3 className="text-xl font-bold text-white text-center">{category.name}</h3>
                </div>
              </Link>
            ))
          )}
        </div>
      </section>

      {/* Featured Products */}
      <section className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="flex justify-between items-center mb-8">
          <h2 className="text-3xl font-extrabold text-gray-900">Produtos em Destaque</h2>
          <Link to="/products" className="text-primary-600 font-bold flex items-center hover:underline">
            Ver todos <ArrowRight className="ml-1 w-4 h-4" />
          </Link>
        </div>
        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-8">
          {productsLoading ? (
            Array.from({ length: 4 }).map((_, i) => (
              <div key={i} className="h-[400px] bg-gray-100 animate-pulse rounded-2xl"></div>
            ))
          ) : (
            products?.slice(0, 8).map((product) => (
              <ProductCard key={product.id} product={product} />
            ))
          )}
        </div>
      </section>

      {/* Newsletter Section */}
      <section className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="bg-gray-100 rounded-3xl p-8 md:p-16 flex flex-col md:flex-row items-center justify-between gap-8">
          <div>
            <h2 className="text-3xl font-extrabold text-gray-900 mb-2">Fique por dentro das novidades</h2>
            <p className="text-gray-600">Assine nossa newsletter e receba promoções exclusivas.</p>
          </div>
          <div className="w-full md:w-auto flex flex-col sm:flex-row gap-4">
            <input 
              type="email" 
              placeholder="Seu melhor e-mail" 
              className="px-6 py-4 rounded-full border border-gray-300 focus:outline-none focus:ring-2 focus:ring-primary-500 w-full md:w-80"
            />
            <button className="bg-primary-600 text-white px-8 py-4 rounded-full font-bold hover:bg-primary-700 transition">
              Inscrever-se
            </button>
          </div>
        </div>
      </section>
    </div>
  );
};

export default Home;
