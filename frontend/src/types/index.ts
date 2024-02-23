export interface Category {
  id?: string;
  name: string;
}

export interface Product {
  id?: string;
  name: string;
  description: string;
  price: number;
  category: Category;
  reference: string;
  referenceBig: string;
}

export interface User {
  id?: string;
  name: string;
  email: string;
  password?: string;
  role: 'ROLE_USER' | 'ROLE_ADMIN';
}

export interface CartItem {
  product: Product;
  quantity: number;
}

export interface Cart {
  items: CartItem[];
  totalPrice: number;
}

export interface OrderDetail {
  product: Product;
  price: number;
  quantity: number;
}

export interface Order {
  id?: string;
  customer?: User;
  details: OrderDetail[];
  totalPrice: number;
  status: 'PENDING' | 'PAID' | 'SHIPPED' | 'DELIVERED' | 'CANCELLED';
  createdAt: string;
}

export interface AuthResponse {
  token: string;
  user: User;
}
