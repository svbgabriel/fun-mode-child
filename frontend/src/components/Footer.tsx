import React from 'react';
import { Mail, Phone, MapPin } from 'lucide-react';
import { useQuery } from '@tanstack/react-query';
import api from '../services/api';
import { Category } from '../types';
import { Link } from 'react-router-dom';

const Footer: React.FC = () => {
  const { data: categories } = useQuery<Category[]>({
    queryKey: ['categories'],
    queryFn: async () => {
      const response = await api.get('/categories');
      return response.data;
    },
  });

  return (
    <footer className="bg-gray-900 text-white pt-12 pb-8">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="grid grid-cols-1 md:grid-cols-4 gap-8 mb-8">
          <div>
            <h3 className="text-xl font-bold mb-4 text-primary-400">FunModeChild</h3>
            <p className="text-gray-400 text-sm">
              Sua loja de brinquedos favorita! Oferecemos os melhores produtos para a diversão de seus pequenos.
            </p>
          </div>
          <div>
            <h4 className="text-lg font-semibold mb-4">Acesso Rápido</h4>
            <ul className="space-y-2 text-gray-400 text-sm">
              <li><Link to="/products" className="hover:text-primary-400">Produtos</Link></li>
              <li><Link to="/cart" className="hover:text-primary-400">Carrinho</Link></li>
              <li><Link to="/login" className="hover:text-primary-400">Entrar</Link></li>
            </ul>
          </div>
          <div>
            <h4 className="text-lg font-semibold mb-4">Categorias</h4>
            <ul className="space-y-2 text-gray-400 text-sm">
              {categories?.slice(0, 5).map((category) => (
                <li key={category.id}>
                  <Link to={`/categories/${category.id}`} className="hover:text-primary-400">
                    {category.name}
                  </Link>
                </li>
              ))}
              <li><Link to="/products" className="hover:text-primary-400 italic">Ver todas...</Link></li>
            </ul>
          </div>
          <div>
            <h4 className="text-lg font-semibold mb-4">Contato</h4>
            <ul className="space-y-3 text-gray-400 text-sm">
              <li className="flex items-center space-x-2">
                <MapPin className="w-4 h-4 text-primary-400 flex-shrink-0" />
                <span>Rua das Crianças, 123 - Centro, São Paulo - SP</span>
              </li>
              <li className="flex items-center space-x-2">
                <Phone className="w-4 h-4 text-primary-400 flex-shrink-0" />
                <span>(11) 98765-4321</span>
              </li>
              <li className="flex items-center space-x-2">
                <Mail className="w-4 h-4 text-primary-400 flex-shrink-0" />
                <span>contato@funmodechild.com.br</span>
              </li>
            </ul>
          </div>
        </div>
        <div className="border-t border-gray-800 pt-8 text-center text-gray-500 text-sm">
          <p>© {new Date().getFullYear()} FunModeChild. Todos os direitos reservados.</p>
        </div>
      </div>
    </footer>
  );
};

export default Footer;
