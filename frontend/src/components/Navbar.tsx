import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { ShoppingCart, User, LogOut, Menu, X } from 'lucide-react';
import { useAuthStore } from '../store/useAuthStore';
import { useCartStore } from '../store/useCartStore';

const Navbar: React.FC = () => {
  const { user, logout } = useAuthStore();
  const { items } = useCartStore();
  const [isOpen, setIsOpen] = React.useState(false);
  const navigate = useNavigate();

  const cartItemCount = items.reduce((acc, item) => acc + item.quantity, 0);

  const handleLogout = () => {
    logout();
    navigate('/login');
  };

  return (
    <nav className="bg-white shadow-sm sticky top-0 z-50">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="flex justify-between h-16">
          <div className="flex items-center">
            <Link to="/" className="flex-shrink-0 flex items-center">
              <span className="text-2xl font-bold text-primary-600">FunModeChild</span>
            </Link>
          </div>

          {/* Desktop menu */}
          <div className="hidden md:flex items-center space-x-8">
            <Link to="/products" className="text-gray-700 hover:text-primary-600 font-medium">Produtos</Link>
            
            <Link to="/cart" className="relative p-2 text-gray-700 hover:text-primary-600">
              <ShoppingCart className="w-6 h-6" />
              {cartItemCount > 0 && (
                <span className="absolute top-0 right-0 inline-flex items-center justify-center px-2 py-1 text-xs font-bold leading-none text-white transform translate-x-1/2 -translate-y-1/2 bg-red-600 rounded-full">
                  {cartItemCount}
                </span>
              )}
            </Link>

            {user ? (
              <div className="flex items-center space-x-4">
                <Link to="/profile" className="flex items-center space-x-1 text-gray-700 hover:text-primary-600">
                  <User className="w-5 h-5" />
                  <span className="font-medium">{user.name}</span>
                </Link>
                {user.role === 'ROLE_ADMIN' && (
                  <Link to="/admin" className="text-gray-700 hover:text-primary-600 font-medium underline">Admin</Link>
                )}
                <button onClick={handleLogout} className="text-gray-700 hover:text-red-600">
                  <LogOut className="w-5 h-5" />
                </button>
              </div>
            ) : (
              <Link to="/login" className="bg-primary-600 text-white px-4 py-2 rounded-md font-medium hover:bg-primary-700 transition">
                Entrar
              </Link>
            )}
          </div>

          {/* Mobile menu button */}
          <div className="md:hidden flex items-center">
            <button onClick={() => setIsOpen(!isOpen)} className="text-gray-700">
              {isOpen ? <X className="w-6 h-6" /> : <Menu className="w-6 h-6" />}
            </button>
          </div>
        </div>
      </div>

      {/* Mobile menu */}
      {isOpen && (
        <div className="md:hidden bg-white border-t">
          <div className="px-2 pt-2 pb-3 space-y-1">
            <Link to="/products" className="block px-3 py-2 text-gray-700 font-medium">Produtos</Link>
            <Link to="/cart" className="block px-3 py-2 text-gray-700 font-medium">Carrinho ({cartItemCount})</Link>
            {user ? (
              <>
                <Link to="/profile" className="block px-3 py-2 text-gray-700 font-medium">Perfil ({user.name})</Link>
                {user.role === 'ROLE_ADMIN' && (
                  <Link to="/admin" className="block px-3 py-2 text-gray-700 font-medium">Admin</Link>
                )}
                <button onClick={handleLogout} className="block w-full text-left px-3 py-2 text-red-600 font-medium">Sair</button>
              </>
            ) : (
              <Link to="/login" className="block px-3 py-2 text-primary-600 font-medium">Entrar</Link>
            )}
          </div>
        </div>
      )}
    </nav>
  );
};

export default Navbar;
