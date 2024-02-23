import React from 'react';
import { useQuery, useMutation } from '@tanstack/react-query';
import { useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { z } from 'zod';
import api from '../services/api';
import { useAuthStore } from '../store/useAuthStore';
import { Order } from '../types';
import { 
  User as UserIcon, 
  Package, 
  Lock, 
  ChevronRight, 
  CheckCircle2, 
  Clock, 
  Truck, 
  AlertCircle,
  LogOut
} from 'lucide-react';
import { useNavigate } from 'react-router-dom';

const passwordSchema = z.object({
  oldPassword: z.string().min(1, 'Senha atual é obrigatória'),
  newPassword: z.string().min(6, 'A nova senha deve ter pelo menos 6 caracteres'),
  newPasswordConfirm: z.string()
}).refine((data) => data.newPassword === data.newPasswordConfirm, {
  message: "As senhas não coincidem",
  path: ["newPasswordConfirm"],
});

type PasswordFormValues = z.infer<typeof passwordSchema>;

const Profile: React.FC = () => {
  const { user, logout } = useAuthStore();
  const navigate = useNavigate();
  const [activeTab, setActiveTab] = React.useState<'orders' | 'security'>('orders');
  const [passwordSuccess, setPasswordSuccess] = React.useState(false);
  const [passwordError, setPasswordError] = React.useState<string | null>(null);

  const { data: orders, isLoading: ordersLoading } = useQuery<Order[]>({
    queryKey: ['orders'],
    queryFn: async () => {
      const response = await api.get('/orders/customer');
      return response.data;
    },
  });

  const passwordMutation = useMutation({
    mutationFn: (data: PasswordFormValues) => api.put('/users', data),
    onSuccess: () => {
      setPasswordSuccess(true);
      setPasswordError(null);
      reset();
      setTimeout(() => setPasswordSuccess(false), 5000);
    },
    onError: (err: any) => {
      setPasswordError(err.response?.data?.message || 'Erro ao alterar senha. Verifique se a senha atual está correta.');
    }
  });

  const { register, handleSubmit, reset, formState: { errors } } = useForm<PasswordFormValues>({
    resolver: zodResolver(passwordSchema),
  });

  const handleLogout = () => {
    logout();
    navigate('/');
  };

  const getStatusIcon = (status: string) => {
    switch (status) {
      case 'DELIVERED': return <CheckCircle2 className="w-5 h-5 text-green-500" />;
      case 'SHIPPED': return <Truck className="w-5 h-5 text-blue-500" />;
      case 'PAID': return <Clock className="w-5 h-5 text-yellow-500" />;
      case 'CANCELLED': return <AlertCircle className="w-5 h-5 text-red-500" />;
      default: return <Clock className="w-5 h-5 text-gray-500" />;
    }
  };

  const getStatusLabel = (status: string) => {
    switch (status) {
      case 'DELIVERED': return 'Entregue';
      case 'SHIPPED': return 'Em trânsito';
      case 'PAID': return 'Pago';
      case 'PENDING': return 'Pendente';
      case 'CANCELLED': return 'Cancelado';
      default: return status;
    }
  };

  const formatPrice = (price: number) => {
    return new Intl.NumberFormat('pt-BR', {
      style: 'currency',
      currency: 'BRL',
    }).format(price);
  };

  if (!user) return null;

  return (
    <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-12">
      <div className="flex flex-col lg:flex-row gap-12">
        {/* Sidebar */}
        <aside className="w-full lg:w-80">
          <div className="bg-white rounded-3xl border border-gray-100 shadow-sm overflow-hidden">
            <div className="p-8 bg-primary-600 text-white text-center">
              <div className="inline-flex items-center justify-center p-4 bg-white/20 rounded-full mb-4">
                <UserIcon className="w-12 h-12" />
              </div>
              <h2 className="text-xl font-bold">{user.name}</h2>
              <p className="text-primary-100 text-sm">{user.email}</p>
            </div>
            
            <nav className="p-4">
              <button 
                onClick={() => setActiveTab('orders')}
                className={`w-full flex items-center gap-3 px-4 py-4 rounded-xl font-bold transition-all ${
                  activeTab === 'orders' ? 'bg-primary-50 text-primary-600' : 'text-gray-500 hover:bg-gray-50'
                }`}
              >
                <Package className="w-5 h-5" />
                Meus Pedidos
              </button>
              <button 
                onClick={() => setActiveTab('security')}
                className={`w-full flex items-center gap-3 px-4 py-4 rounded-xl font-bold transition-all ${
                  activeTab === 'security' ? 'bg-primary-50 text-primary-600' : 'text-gray-500 hover:bg-gray-50'
                }`}
              >
                <Lock className="w-5 h-5" />
                Segurança
              </button>
              <div className="border-t border-gray-100 my-4"></div>
              <button 
                onClick={handleLogout}
                className="w-full flex items-center gap-3 px-4 py-4 rounded-xl font-bold text-red-500 hover:bg-red-50 transition-all"
              >
                <LogOut className="w-5 h-5" />
                Sair da Conta
              </button>
            </nav>
          </div>
        </aside>

        {/* Content */}
        <main className="flex-grow">
          {activeTab === 'orders' ? (
            <div className="space-y-8">
              <h1 className="text-3xl font-black text-gray-900">Meus Pedidos</h1>
              
              {ordersLoading ? (
                <div className="space-y-6">
                  {[1, 2].map(i => (
                    <div key={i} className="h-48 bg-gray-100 animate-pulse rounded-3xl"></div>
                  ))}
                </div>
              ) : orders && orders.length > 0 ? (
                <div className="space-y-6">
                  {orders.map((order) => (
                    <div key={order.id} className="bg-white rounded-3xl border border-gray-100 shadow-sm overflow-hidden group hover:shadow-md transition">
                      <div className="p-6 md:p-8 flex flex-col md:flex-row md:items-center justify-between gap-6">
                        <div className="space-y-1">
                          <p className="text-xs font-bold text-gray-400 uppercase tracking-widest">Pedido #{order.id?.slice(-8).toUpperCase()}</p>
                          <p className="text-sm font-medium text-gray-500">Realizado em {new Date(order.createdAt).toLocaleDateString('pt-BR')}</p>
                          <div className="flex items-center gap-2 pt-2">
                            {getStatusIcon(order.status)}
                            <span className="text-sm font-bold text-gray-900">{getStatusLabel(order.status)}</span>
                          </div>
                        </div>
                        
                        <div className="flex items-center gap-8">
                          <div className="text-right">
                            <p className="text-xs font-bold text-gray-400 uppercase tracking-widest">Total</p>
                            <p className="text-xl font-black text-primary-600">{formatPrice(order.totalPrice)}</p>
                          </div>
                          <ChevronRight className="w-6 h-6 text-gray-300 group-hover:text-primary-600 transition" />
                        </div>
                      </div>
                      
                      {/* Mini thumbnails of items */}
                      <div className="px-8 pb-8 flex gap-3">
                        {order.details?.map((item, idx) => (
                          <div key={idx} className="w-12 h-12 rounded-lg bg-gray-50 border border-gray-100 overflow-hidden">
                            <img 
                              src={item.product.reference ? `/api${item.product.reference}` : 'https://images.unsplash.com/photo-1515488042361-ee00e0ddd4e4?auto=format&fit=crop&q=80&w=1475'} 
                              alt={item.product.name} 
                              className="w-full h-full object-cover"
                            />
                          </div>
                        ))}
                      </div>
                    </div>
                  ))}
                </div>
              ) : (
                <div className="text-center py-20 bg-gray-50 rounded-[40px] border-2 border-dashed border-gray-200">
                  <div className="inline-flex items-center justify-center p-6 bg-white rounded-full text-gray-300 mb-6 shadow-sm">
                    <Package className="w-12 h-12" />
                  </div>
                  <h3 className="text-2xl font-bold text-gray-900 mb-2">Nenhum pedido ainda</h3>
                  <p className="text-gray-500 max-w-xs mx-auto">Você ainda não realizou compras em nossa loja.</p>
                  <button 
                    onClick={() => navigate('/products')}
                    className="mt-8 bg-primary-600 text-white px-8 py-3 rounded-2xl font-bold hover:bg-primary-700 transition shadow-lg shadow-primary-200"
                  >
                    Começar a Comprar
                  </button>
                </div>
              )}
            </div>
          ) : (
            <div className="max-w-xl space-y-8">
              <h1 className="text-3xl font-black text-gray-900">Segurança</h1>
              <p className="text-gray-500">Altere sua senha de acesso para manter sua conta segura.</p>

              <form onSubmit={handleSubmit(data => passwordMutation.mutate(data))} className="bg-white p-8 rounded-3xl border border-gray-100 shadow-sm space-y-6">
                {passwordSuccess && (
                  <div className="p-4 bg-green-50 border-l-4 border-green-500 text-green-700 font-medium rounded-r-xl flex items-center gap-3">
                    <CheckCircle2 className="w-5 h-5" />
                    Senha alterada com sucesso!
                  </div>
                )}
                
                {passwordError && (
                  <div className="p-4 bg-red-50 border-l-4 border-red-500 text-red-700 font-medium rounded-r-xl flex items-center gap-3">
                    <AlertCircle className="w-5 h-5" />
                    {passwordError}
                  </div>
                )}

                <div>
                  <label className="block text-sm font-bold text-gray-700 mb-2">Senha Atual</label>
                  <input
                    {...register('oldPassword')}
                    type="password"
                    className={`block w-full px-4 py-4 border-2 rounded-2xl focus:outline-none focus:ring-2 focus:ring-primary-500 transition-all ${
                      errors.oldPassword ? 'border-red-300' : 'border-gray-100 hover:border-primary-300'
                    }`}
                  />
                  {errors.oldPassword && <p className="mt-2 text-sm text-red-600 font-medium">{errors.oldPassword.message}</p>}
                </div>

                <div className="border-t border-gray-100 pt-6">
                  <label className="block text-sm font-bold text-gray-700 mb-2">Nova Senha</label>
                  <input
                    {...register('newPassword')}
                    type="password"
                    className={`block w-full px-4 py-4 border-2 rounded-2xl focus:outline-none focus:ring-2 focus:ring-primary-500 transition-all ${
                      errors.newPassword ? 'border-red-300' : 'border-gray-100 hover:border-primary-300'
                    }`}
                  />
                  {errors.newPassword && <p className="mt-2 text-sm text-red-600 font-medium">{errors.newPassword.message}</p>}
                </div>

                <div>
                  <label className="block text-sm font-bold text-gray-700 mb-2">Confirmar Nova Senha</label>
                  <input
                    {...register('newPasswordConfirm')}
                    type="password"
                    className={`block w-full px-4 py-4 border-2 rounded-2xl focus:outline-none focus:ring-2 focus:ring-primary-500 transition-all ${
                      errors.newPasswordConfirm ? 'border-red-300' : 'border-gray-100 hover:border-primary-300'
                    }`}
                  />
                  {errors.newPasswordConfirm && <p className="mt-2 text-sm text-red-600 font-medium">{errors.newPasswordConfirm.message}</p>}
                </div>

                <button
                  type="submit"
                  disabled={passwordMutation.isPending}
                  className="w-full bg-gray-900 text-white py-4 px-6 rounded-2xl font-bold text-lg hover:bg-black transition shadow-lg disabled:opacity-50"
                >
                  {passwordMutation.isPending ? 'Alterando...' : 'Salvar Nova Senha'}
                </button>
              </form>
            </div>
          )}
        </main>
      </div>
    </div>
  );
};

export default Profile;
