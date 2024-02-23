import React from 'react';
import { useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { z } from 'zod';
import { useNavigate, Navigate } from 'react-router-dom';
import { useCartStore } from '../store/useCartStore';
import { useAuthStore } from '../store/useAuthStore';
import api from '../services/api';
import { CreditCard, Lock, ShieldCheck, CheckCircle2, ArrowRight, ArrowLeft } from 'lucide-react';

const paymentSchema = z.object({
  cardNumber: z.string().regex(/^\d{16}$/, 'Número do cartão inválido (16 dígitos)'),
  cardName: z.string().min(3, 'Nome no cartão é obrigatório'),
  expiry: z.string().regex(/^(0[1-9]|1[0-2])\/\d{2}$/, 'Data de expiração inválida (MM/AA)'),
  cvv: z.string().regex(/^\d{3}$/, 'CVV inválido (3 dígitos)'),
  installments: z.string().min(1, 'Selecione o número de parcelas'),
});

type PaymentFormValues = z.infer<typeof paymentSchema>;

const Checkout: React.FC = () => {
  const { items, clearCart } = useCartStore();
  const { user } = useAuthStore();
  const navigate = useNavigate();
  const [loading, setLoading] = React.useState(false);
  const [success, setSuccess] = React.useState(false);
  const [error, setError] = React.useState<string | null>(null);

  const totalPrice = items.reduce((acc, item) => acc + item.product.price * item.quantity, 0);

  const { register, handleSubmit, formState: { errors } } = useForm<PaymentFormValues>({
    resolver: zodResolver(paymentSchema),
    defaultValues: {
      installments: "1"
    }
  });

  if (!user) {
    return <Navigate to="/login?redirect=/checkout" replace />;
  }

  if (items.length === 0 && !success) {
    return <Navigate to="/cart" replace />;
  }

  const formatPrice = (price: number) => {
    return new Intl.NumberFormat('pt-BR', {
      style: 'currency',
      currency: 'BRL',
    }).format(price);
  };

  const onSubmit = async (data: PaymentFormValues) => {
    setLoading(true);
    setError(null);
    try {
      // 1. Create Order
      const orderRequest = {
        details: items.map(item => ({
          productId: item.product.id,
          quantity: item.quantity
        }))
      };
      
      const orderResponse = await api.post('/orders', orderRequest);
      const orderId = orderResponse.data.id;

      // 2. Process Payment
      const [month, year] = data.expiry.split('/');
      const paymentRequest = {
        cardNumber: data.cardNumber,
        cardName: data.cardName,
        month: parseInt(month),
        year: parseInt(`20${year}`),
        cvv: parseInt(data.cvv),
        statements: parseInt(data.installments)
      };

      await api.post(`/orders/${orderId}/payment`, paymentRequest);
      
      setSuccess(true);
      clearCart();
    } catch (err: any) {
      setError(err.response?.data?.message || 'Erro ao processar pagamento. Tente novamente.');
    } finally {
      setLoading(false);
    }
  };

  if (success) {
    return (
      <div className="max-w-3xl mx-auto px-4 py-20 text-center">
        <div className="inline-flex items-center justify-center p-6 bg-green-100 rounded-full text-green-600 mb-8 shadow-sm">
          <CheckCircle2 className="w-20 h-20" />
        </div>
        <h1 className="text-4xl font-black text-gray-900 mb-4">Pedido Realizado com Sucesso!</h1>
        <p className="text-gray-500 text-lg mb-10 max-w-md mx-auto">
          Obrigado por comprar na FunModeChild. Você receberá um e-mail com os detalhes do seu pedido em breve.
        </p>
        <button 
          onClick={() => navigate('/products')}
          className="bg-primary-600 text-white px-10 py-4 rounded-2xl font-bold text-lg hover:bg-primary-700 transition shadow-lg shadow-primary-200"
        >
          Voltar para a Loja
        </button>
      </div>
    );
  }

  return (
    <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-12">
      <button 
        onClick={() => navigate('/cart')}
        className="flex items-center text-gray-500 hover:text-primary-600 font-bold mb-8 transition"
      >
        <ArrowLeft className="w-5 h-5 mr-2" />
        Voltar para o Carrinho
      </button>

      <div className="flex flex-col lg:flex-row gap-12">
        {/* Payment Form */}
        <div className="flex-grow">
          <h1 className="text-3xl font-black text-gray-900 mb-8 flex items-center gap-3">
            <CreditCard className="w-8 h-8 text-primary-600" />
            Pagamento
          </h1>

          {error && (
            <div className="mb-8 p-4 bg-red-50 border-l-4 border-red-500 text-red-700 font-medium rounded-r-xl">
              {error}
            </div>
          )}

          <form onSubmit={handleSubmit(onSubmit)} className="space-y-6">
            <div className="bg-white p-8 rounded-3xl border border-gray-100 shadow-sm space-y-6">
              <div>
                <label className="block text-sm font-bold text-gray-700 mb-2">Número do Cartão</label>
                <div className="relative">
                  <span className="absolute inset-y-0 left-0 pl-4 flex items-center text-gray-400">
                    <CreditCard className="w-5 h-5" />
                  </span>
                  <input
                    {...register('cardNumber')}
                    type="text"
                    maxLength={16}
                    className={`block w-full pl-12 pr-4 py-4 border-2 rounded-2xl focus:outline-none focus:ring-2 focus:ring-primary-500 transition-all ${
                      errors.cardNumber ? 'border-red-300' : 'border-gray-100 hover:border-primary-300'
                    }`}
                    placeholder="0000 0000 0000 0000"
                  />
                </div>
                {errors.cardNumber && <p className="mt-2 text-sm text-red-600 font-medium">{errors.cardNumber.message}</p>}
              </div>

              <div>
                <label className="block text-sm font-bold text-gray-700 mb-2">Nome no Cartão</label>
                <input
                  {...register('cardName')}
                  type="text"
                  className={`block w-full px-4 py-4 border-2 rounded-2xl focus:outline-none focus:ring-2 focus:ring-primary-500 transition-all ${
                    errors.cardName ? 'border-red-300' : 'border-gray-100 hover:border-primary-300'
                  }`}
                  placeholder="COMO ESTÁ NO CARTÃO"
                />
                {errors.cardName && <p className="mt-2 text-sm text-red-600 font-medium">{errors.cardName.message}</p>}
              </div>

              <div className="grid grid-cols-2 gap-6">
                <div>
                  <label className="block text-sm font-bold text-gray-700 mb-2">Validade (MM/AA)</label>
                  <input
                    {...register('expiry')}
                    type="text"
                    maxLength={5}
                    className={`block w-full px-4 py-4 border-2 rounded-2xl focus:outline-none focus:ring-2 focus:ring-primary-500 transition-all ${
                      errors.expiry ? 'border-red-300' : 'border-gray-100 hover:border-primary-300'
                    }`}
                    placeholder="MM/AA"
                  />
                  {errors.expiry && <p className="mt-2 text-sm text-red-600 font-medium">{errors.expiry.message}</p>}
                </div>
                <div>
                  <label className="block text-sm font-bold text-gray-700 mb-2">CVV</label>
                  <div className="relative">
                    <span className="absolute inset-y-0 left-0 pl-4 flex items-center text-gray-400">
                      <Lock className="w-5 h-5" />
                    </span>
                    <input
                      {...register('cvv')}
                      type="text"
                      maxLength={3}
                      className={`block w-full pl-12 pr-4 py-4 border-2 rounded-2xl focus:outline-none focus:ring-2 focus:ring-primary-500 transition-all ${
                        errors.cvv ? 'border-red-300' : 'border-gray-100 hover:border-primary-300'
                      }`}
                      placeholder="000"
                    />
                  </div>
                  {errors.cvv && <p className="mt-2 text-sm text-red-600 font-medium">{errors.cvv.message}</p>}
                </div>
              </div>

              <div>
                <label className="block text-sm font-bold text-gray-700 mb-2">Parcelas</label>
                <select
                  {...register('installments')}
                  className="block w-full px-4 py-4 border-2 border-gray-100 rounded-2xl focus:outline-none focus:ring-2 focus:ring-primary-500 hover:border-primary-300 transition-all bg-white"
                >
                  <option value="1">1x de {formatPrice(totalPrice)} sem juros</option>
                  <option value="2">2x de {formatPrice(totalPrice / 2)} sem juros</option>
                  <option value="3">3x de {formatPrice(totalPrice / 3)} sem juros</option>
                  <option value="4">4x de {formatPrice(totalPrice / 4)} sem juros</option>
                  <option value="5">5x de {formatPrice(totalPrice / 5)} sem juros</option>
                  <option value="10">10x de {formatPrice(totalPrice / 10)} sem juros</option>
                </select>
              </div>
            </div>

            <div className="flex items-center gap-2 text-gray-500 justify-center">
              <ShieldCheck className="w-5 h-5 text-green-500" />
              <span className="text-sm font-medium">Ambiente de pagamento 100% seguro</span>
            </div>
          </form>
        </div>

        {/* Order Summary */}
        <aside className="w-full lg:w-96">
          <div className="bg-gray-900 text-white rounded-[40px] p-10 shadow-2xl sticky top-24">
            <h2 className="text-2xl font-black mb-8">Resumo</h2>
            
            <div className="max-h-60 overflow-y-auto mb-8 space-y-4 pr-2 custom-scrollbar">
              {items.map((item) => (
                <div key={item.product.id} className="flex gap-4">
                  <div className="w-12 h-12 rounded-lg bg-white overflow-hidden flex-shrink-0">
                    <img 
                      src={item.product.reference ? `/api${item.product.reference}` : 'https://images.unsplash.com/photo-1515488042361-ee00e0ddd4e4?auto=format&fit=crop&q=80&w=1475'} 
                      alt={item.product.name} 
                      className="w-full h-full object-cover"
                    />
                  </div>
                  <div className="flex-grow">
                    <p className="text-sm font-bold line-clamp-1">{item.product.name}</p>
                    <p className="text-xs text-gray-400">{item.quantity}x {formatPrice(item.product.price)}</p>
                  </div>
                </div>
              ))}
            </div>

            <div className="space-y-6 mb-10 border-b border-gray-800 pb-8">
              <div className="flex justify-between font-medium text-gray-400">
                <span>Itens</span>
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
              onClick={handleSubmit(onSubmit)}
              disabled={loading}
              className="w-full bg-primary-600 text-white py-5 rounded-2xl font-black text-lg hover:bg-primary-700 transition shadow-lg shadow-primary-900/50 flex items-center justify-center gap-3 disabled:opacity-50"
            >
              {loading ? 'Processando...' : (
                <>
                  PAGAR AGORA <ArrowRight className="w-6 h-6" />
                </>
              )}
            </button>
          </div>
        </aside>
      </div>
    </div>
  );
};

export default Checkout;
