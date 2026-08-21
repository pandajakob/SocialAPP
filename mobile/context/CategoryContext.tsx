import { createContext, useContext, useEffect, useState } from "react";

import { API_BASE } from "@/constants/api";
import { Category } from "@/types/category";
import { CategoryContextType } from "@/types/CategoryContextType";
import { useAuth } from "./AuthContext";

export const CategoryContext = createContext<
  CategoryContextType | undefined
>(undefined);

export function CategoryProvider({
  children,
}: {
  children: React.ReactNode;
}) {
  const [categories, setCategories] = useState<Category[]>([]);
  const [mainCategories, setMainCategories] = useState<Category[]>([]);
  const [loading, setLoading] = useState(true);
  const { token } = useAuth();
  
  const getAllCategories = async (): Promise<Category[]> => {
    const response = await fetch(`${API_BASE}/api/categories`, {
    method: "GET",

    headers: {
        "Content-Type": "application/json",
        Cookie: `token=${token}`,
    },
    });
    if (!response.ok) {
      throw new Error(
        `Failed to get categories: ${response.status} ${response.statusText}`
      );
    }

    return response.json();
  };

  const getAllMainCategories = async (): Promise<Category[]> => {
    setLoading(true)
    try {
        const response = await fetch(`${API_BASE}/api/categories/main`, {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
                Cookie: `token=${token}`,
            },
        });

        if (!response.ok) {
            throw new Error(
                `Failed to get main categories: ${response.status} ${response.statusText}`
            );
        }
        const data =  await response.json()
        const categoriesResponse: Category[] = data.map((cat: any) => ({
                id: cat.id,
                name: cat.name,
                parentCategoryId: cat.parentCategoryId
              }));
    
      return categoriesResponse;
   } catch (error: any) {
      throw new Error(error?.message ?? "Unknown main category fetch error");
    } finally {
      setLoading(false);
    }

  };

  const getCategoryById = async (id: number): Promise<Category> => {
    const response = await fetch(`${API_BASE}/api/categories/${id}`);

    if (!response.ok) {
      throw new Error(`Failed to get category: ${response.status}`);
    }

    return response.json();
  };

  const getCategoryByName = async (name: string): Promise<Category> => {
    const response = await fetch(
      `${API_BASE}/api/categories/name/${encodeURIComponent(name)}`
    );

    if (!response.ok) {
      throw new Error(`Failed to get category: ${response.status}`);
    }

    return response.json();
  };

  const getSubCategories = async (name: string): Promise<Category[]> => {
    const response = await fetch(
      `${API_BASE}/api/categories/sub/${encodeURIComponent(name)}`
    );

    if (!response.ok) {
      throw new Error(`Failed to get subcategories: ${response.status}`);
    }

    return response.json();
  };

  useEffect(() => {
    const loadCategories = async () => {
      try {
        setLoading(true);

        const [allCategories, mainCategories] = await Promise.all([
          await getAllCategories(),
          await getAllMainCategories(),
        ]);

        setCategories(allCategories);

        setMainCategories(mainCategories);
      } catch (error) {
        console.error("Error loading categories:", error);
      } finally {
        setLoading(false);
      }
    };

    loadCategories();
  }, []);

  return (
    <CategoryContext.Provider
      value={{
        categories,
        mainCategories,
        loading,
        getCategoryById,
        getCategoryByName,
        getSubCategories,
      }}
    >
      {children}
    </CategoryContext.Provider>
  );
}

export const useCategory = () => {
  const ctx = useContext(CategoryContext);

  if (!ctx) {
    throw new Error("useCategory must be used inside CategoryProvider");
  }

  return ctx;
};