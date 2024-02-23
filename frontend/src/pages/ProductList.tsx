import React from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { useQuery } from '@tanstack/react-query';
import api from '../services/api';
import { Product, Category } from '../types';
import ProductCard from '../components/ProductCard';
import { Filter, Search, SlidersHorizontal } from 'lucide-react';

const ProductList: React.FC = () => {
  const { categoryId } = useParams<{ categoryId: string }>();
  const navigate = useNavigate();
  const [search, setSearch] = React.useState('');
  const [selectedCategory, setSelectedCategory] = React.useState<string | null>(null);

  // Sync categoryId from URL to state
  React.useEffect(() => {
    setSelectedCategory(categoryId || null);
  }, [categoryId]);

  const handleCategorySelect = (id: string | null) => {
    if (id) {
      navigate(`/categories/${id}`);
    } else {
      navigate('/products');
    }
  };

  const { data: products, isLoading: productsLoading } = useQuery<Product[]>({
    queryKey: ['products'],
    queryFn: async () => {
      const response = await api.get('/products');
      return response.data;
    },
  });

  const { data: categories } = useQuery<Category[]>({
    queryKey: ['categories'],
    queryFn: async () => {
      const response = await api.get('/categories');
      return response.data;
    },
  });

  const filteredProducts = products?.filter(product => {
    const matchesSearch = product.name.toLowerCase().includes(search.toLowerCase()) || 
                          product.description.toLowerCase().includes(search.toLowerCase());
    const matchesCategory = selectedCategory ? product.category.id === selectedCategory : true;
    return matchesSearch && matchesCategory;
  });

  const currentCategoryName = categories?.find(c => c.id === selectedCategory)?.name;

  return (
    <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-12">
      <div className="flex flex-col md:flex-row md:items-center justify-between gap-6 mb-12">
        <div>
          <h1 className="text-4xl font-black text-gray-900 mb-2">
            {selectedCategory ? `Brinquedos: ${currentCategoryName}` : 'Todos os Produtos'}
          </h1>
          <p className="text-gray-500 font-medium">Explore nossa seleção completa de brinquedos</p>
        </div>
        
        <div className="flex flex-col sm:flex-row gap-4 w-full md:w-auto">
          <div className="relative w-full sm:w-80">
            <span className="absolute inset-y-0 left-0 pl-4 flex items-center text-gray-400">
              <Search className="w-5 h-5" />
            </span>
            <input
              type="text"
              placeholder="Buscar brinquedos..."
              value={search}
              onChange={(e) => setSearch(e.target.value)}
              className="block w-full pl-12 pr-4 py-3 border-2 border-gray-100 rounded-2xl focus:outline-none focus:ring-2 focus:ring-primary-500 hover:border-primary-300 transition-all"
            />
          </div>
          <button className="flex items-center justify-center gap-2 px-6 py-3 border-2 border-gray-100 rounded-2xl font-bold text-gray-700 hover:border-primary-300 transition-all">
            <SlidersHorizontal className="w-5 h-5" />
            Filtros
          </button>
        </div>
      </div>

      <div className="flex flex-col lg:flex-row gap-12">
        {/* Sidebar Filters */}
        <aside className="w-full lg:w-64 space-y-10">
          <div>
            <h3 className="text-lg font-bold text-gray-900 mb-6 flex items-center gap-2">
              <Filter className="w-5 h-5 text-primary-600" />
              Categorias
            </h3>
            <div className="space-y-3">
              <button
                onClick={() => handleCategorySelect(null)}
                className={`block w-full text-left px-4 py-3 rounded-xl font-bold transition-all ${
                  selectedCategory === null 
                    ? 'bg-primary-600 text-white shadow-lg shadow-primary-200' 
                    : 'bg-gray-50 text-gray-600 hover:bg-gray-100'
                }`}
              >
                Todas
              </button>
              {categories?.map((category) => (
                <button
                  key={category.id}
                  onClick={() => handleCategorySelect(category.id || null)}
                  className={`block w-full text-left px-4 py-3 rounded-xl font-bold transition-all ${
                    selectedCategory === category.id 
                      ? 'bg-primary-600 text-white shadow-lg shadow-primary-200' 
                      : 'bg-gray-50 text-gray-600 hover:bg-gray-100'
                  }`}
                >
                  {category.name}
                </button>
              ))}
            </div>
          </div>

          <div className="bg-primary-50 p-6 rounded-3xl">
            <h4 className="font-bold text-primary-900 mb-2">Oferta Especial</h4>
            <p className="text-primary-700 text-sm mb-4">Ganhe 10% de desconto em sua primeira compra!</p>
            <button className="w-full bg-primary-600 text-white py-3 rounded-xl font-bold text-sm hover:bg-primary-700 transition">
              CADASTRAR
            </button>
          </div>
        </aside>

        {/* Product Grid */}
        <div className="flex-grow">
          {productsLoading ? (
            <div className="grid grid-cols-1 sm:grid-cols-2 xl:grid-cols-3 gap-8">
              {Array.from({ length: 6 }).map((_, i) => (
                <div key={i} className="h-[400px] bg-gray-100 animate-pulse rounded-2xl"></div>
              ))}
            </div>
          ) : filteredProducts && filteredProducts.length > 0 ? (
            <div className="grid grid-cols-1 sm:grid-cols-2 xl:grid-cols-3 gap-8">
              {filteredProducts.map((product) => (
                <ProductCard key={product.id} product={product} />
              ))}
            </div>
          ) : (
            <div className="text-center py-20 bg-gray-50 rounded-3xl">
              <div className="inline-flex items-center justify-center p-6 bg-white rounded-full text-gray-300 mb-4 shadow-sm">
                <Search className="w-12 h-12" />
              </div>
              <h3 className="text-2xl font-bold text-gray-900 mb-2">Nenhum produto encontrado</h3>
              <p className="text-gray-500">Tente ajustar sua busca ou filtros para encontrar o que procura.</p>
              <button 
                onClick={() => {setSearch(''); handleCategorySelect(null);}}
                className="mt-6 font-bold text-primary-600 hover:underline"
              >
                Limpar todos os filtros
              </button>
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

export default ProductList;
