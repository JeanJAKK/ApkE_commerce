import { create } from 'zustand';
import { persist } from 'zustand/middleware';
import type { CartItem } from '@/types';

interface CartState {
  items: CartItem[];
  isOpen: boolean;

  // Actions
  addItem: (item: CartItem) => void;
  removeItem: (itemId: number) => void;
  updateQuantity: (itemId: number, quantity: number) => void;
  clearCart: () => void;
  setCart: (items: CartItem[]) => void;
  toggleCart: () => void;
  openCart: () => void;
  closeCart: () => void;

  // Computed
  getTotalItems: () => number;
  getTotalPrice: () => number;
}

export const useCartStore = create<CartState>()(
  persist(
    (set, get) => ({
      items: [],
      isOpen: false,

      addItem: (item: CartItem) => {
        const items = get().items;
        const existingIndex = items.findIndex(
          (i) =>
            i.productId === item.productId &&
            i.selectedColor === item.selectedColor &&
            i.selectedSize === item.selectedSize
        );

        if (existingIndex >= 0) {
          const newItems = [...items];
          newItems[existingIndex].quantity += item.quantity;
          newItems[existingIndex].totalPrice =
            newItems[existingIndex].discountedPrice * newItems[existingIndex].quantity;
          set({ items: newItems });
        } else {
          set({ items: [...items, item] });
        }
      },

      removeItem: (itemId: number) => {
        set({ items: get().items.filter((item) => item.id !== itemId) });
      },

      updateQuantity: (itemId: number, quantity: number) => {
        const items = get().items;
        if (quantity <= 0) {
          set({ items: items.filter((item) => item.id !== itemId) });
        } else {
          const newItems = items.map((item) =>
            item.id === itemId
              ? {
                  ...item,
                  quantity,
                  totalPrice: item.discountedPrice * quantity,
                }
              : item
          );
          set({ items: newItems });
        }
      },

      clearCart: () => {
        set({ items: [] });
      },

      setCart: (items: CartItem[]) => {
        set({ items });
      },

      toggleCart: () => {
        set({ isOpen: !get().isOpen });
      },

      openCart: () => {
        set({ isOpen: true });
      },

      closeCart: () => {
        set({ isOpen: false });
      },

      getTotalItems: () => {
        return get().items.reduce((sum, item) => sum + item.quantity, 0);
      },

      getTotalPrice: () => {
        return get().items.reduce((sum, item) => sum + item.totalPrice, 0);
      },
    }),
    {
      name: 'cart-storage',
      partialize: (state) => ({
        items: state.items,
      }),
    }
  )
);
