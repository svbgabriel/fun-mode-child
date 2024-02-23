import React from 'react';
import { ShoppingBag, Trash2, Plus, Minus, ArrowRight, ShoppingCart } from 'lucide-react';
import { useCartStore } from '../store/useCartStore';
import { Link, useNavigate } from 'react-router-dom';
import { useAuthStore } from '../store/useAuthStore';

const Cart: React.FC = () => {
  const { items, removeItem, updateQuantity, clearCart } = useCartStore();
  const { user } = useAuthStore();
  const navigate = useNavigate();

  const totalPrice = items.reduce((acc, item) => acc + item.product.price * item.quantity, 0);

  const formatPrice = (price: number) => {
    return new Intl.NumberFormat('pt-BR', {
      style: 'currency',
      currency: 'BRL',
    }).format(price);
  };

  const handleCheckout = () => {
    if (!user) {
      navigate('/login?redirect=/checkout');
      return;
    }
    navigate('/checkout');
  };

  if (items.length === 0) {
    return (
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-20 text-center">
        <div className="inline-flex items-center justify-center p-8 bg-gray-50 rounded-full text-gray-300 mb-8 shadow-inner">
          <ShoppingCart className="w-16 h-16" />
        </div>
        <h2 className="text-4xl font-black text-gray-900 mb-4">Seu carrinho está vazio</h2>
        <p className="text-gray-500 text-lg mb-10 max-w-md mx-auto font-medium">Parece que você ainda não adicionou nenhum brinquedo ao seu carrinho. Que tal começar agora?</p>
        <Link 
          to="/products" 
          className="bg-primary-600 text-white px-10 py-4 rounded-2xl font-bold text-lg hover:bg-primary-700 transition shadow-lg shadow-primary-200 inline-flex items-center gap-2"
        >
          Explorar Produtos <ArrowRight className="w-5 h-5" />
        </Link>
      </div>
    );
  }

  return (
    <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-12">
      <div className="flex flex-col lg:flex-row gap-12">
        {/* Cart Items List */}
        <div className="flex-grow">
          <div className="flex items-center justify-between mb-8">
            <h1 className="text-3xl font-black text-gray-900 flex items-center gap-3">
              <ShoppingBag className="w-8 h-8 text-primary-600" />
              Seu Carrinho
            </h1>
            <button 
              onClick={clearCart}
              className="text-red-500 font-bold hover:text-red-600 flex items-center gap-2 hover:underline"
            >
              <Trash2 className="w-4 h-4" />
              Limpar Carrinho
            </button>
          </div>

          <div className="space-y-6">
            {items.map((item) => (
              <div 
                key={item.product.id} 
                className="bg-white p-6 rounded-3xl border border-gray-100 shadow-sm flex flex-col sm:flex-row items-center gap-6 group hover:shadow-md transition"
              >
                <div className="w-32 h-32 flex-shrink-0 rounded-2xl overflow-hidden bg-gray-50">
                  <img 
                    src={item.product.reference ? `/api${item.product.reference}` : 'https://images.unsplash.com/photo-1515488042361-ee00e0ddd4e4?auto=format&fit=crop&q=80&w=1475'} 
                    alt={item.product.name} 
                    className="w-full h-full object-cover group-hover:scale-110 transition duration-500"
                    onError={(e) => {
                      (e.target as HTMLImageElement).src = 'https://images.unsplash.com/photo-1515488042361-ee00e0ddd4e4?auto=format&fit=crop&q=80&w=1475';
                    }}
                  />
                </div>
                
                <div className="flex-grow text-center sm:text-left">
                  <Link to={`/categories/${item.product.category.id}`} className="text-xs font-bold text-primary-600 uppercase tracking-wider mb-1 block">
                    {item.product.category.name}
                  </Link>
                  <h3 className="text-xl font-bold text-gray-900 mb-2">{item.product.name}</h3>
                  <p className="text-2xl font-black text-primary-600">{formatPrice(item.product.price)}</p>
                </div>

                <div className="flex flex-col sm:items-end gap-4">
                  <div className="flex items-center gap-3 bg-gray-50 p-2 rounded-xl">
                    <button 
                      onClick={() => updateQuantity(item.product.id!, Math.max(1, item.quantity - 1))}
                      className="p-2 bg-white rounded-lg text-gray-600 hover:text-primary-600 shadow-sm transition"
                    >
                      <Minus className="w-4 h-4" />
                    </button>
                    <span className="w-8 text-center font-black text-gray-900">{item.quantity}</span>
                    <button 
                      onClick={() => updateQuantity(item.product.id!, item.quantity + 1)}
                      className="p-2 bg-white rounded-lg text-gray-600 hover:text-primary-600 shadow-sm transition"
                    >
                      <Plus className="w-4 h-4" />
                    </button>
                  </div>
                  <button 
                    onClick={() => removeItem(item.product.id!)}
                    className="text-gray-400 hover:text-red-500 transition-colors flex items-center gap-1 text-sm font-bold"
                  >
                    <Trash2 className="w-4 h-4" />
                    Remover
                  </button>
                </div>
              </div>
            ))}
          </div>
        </div>

        {/* Order Summary */}
        <aside className="w-full lg:w-96">
          <div className="bg-gray-900 text-white rounded-[40px] p-10 shadow-2xl sticky top-24">
            <h2 className="text-2xl font-black mb-8">Resumo do Pedido</h2>
            
            <div className="space-y-6 mb-10 border-b border-gray-800 pb-8">
              <div className="flex justify-between font-medium text-gray-400">
                <span>Subtotal ({items.length} itens)</span>
                <span>{formatPrice(totalPrice)}</span>
              </div>
              <div className="flex justify-between font-medium text-gray-400">
                <span>Frete</span>
                <span className="text-green-400 font-bold uppercase text-xs">Grátis</span>
              </div>
            </div>
            
            <div className="flex justify-between items-end mb-10">
              <span className="text-gray-400 font-bold">Total</span>
              <span className="text-4xl font-black text-primary-400 tracking-tight">
                {formatPrice(totalPrice)}
              </span>
            </div>
            
            <button 
              onClick={handleCheckout}
              className="w-full bg-primary-600 text-white py-5 rounded-2xl font-black text-lg hover:bg-primary-700 transition shadow-lg shadow-primary-900/50 flex items-center justify-center gap-3"
            >
              FINALIZAR COMPRA <ArrowRight className="w-6 h-6" />
            </button>
            
            <Link to="/products" className="block text-center mt-6 text-gray-500 font-bold hover:text-gray-300 transition underline underline-offset-4">
              Continuar Comprando
            </Link>
          </div>
        </aside>
      </div>
    </div>
  );
};

export default Cart;
