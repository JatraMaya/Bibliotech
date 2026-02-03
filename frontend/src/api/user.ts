import type { AddUserProfile, EditUserProfile } from '../types/UserType';
import { getApiPath } from '../utils/utils';

const API_URL = getApiPath();

export const addProfile = async (
  data: AddUserProfile,
  token: string,
): Promise<{ status: string; message: string }> => {
  const formData = new FormData();

  formData.append('bio', data.bio);
  const response = await fetch(`${API_URL}/user/profile`, {
    method: 'POST',
    headers: {
      Authorization: `Bearer ${token}`,
    },
    body: formData,
  });

  const result = await response.json();

  if (!response.ok) {
    throw new Error(result.message || 'Add profile failed');
  }

  return result;
};

export const editProfile = async (
  data: EditUserProfile,
  token: string,
): Promise<{ status: string; message: string }> => {
  const formData = new FormData();

  if (data.firstname) {
    formData.append('firstname', data.firstname);
  }

  if (data.lastname) {
    formData.append('lastname', data.lastname);
  }

  if (data.bio) {
    formData.append('bio', data.bio);
  }

  const response = await fetch(`${API_URL}/user/profile`, {
    method: 'PUT',
    headers: {
      Authorization: `Bearer ${token}`,
    },
    body: formData,
  });

  const result = await response.json();

  if (!response.ok) {
    throw Error(result.message || 'Failed to edit profile');
  }

  return result;
};
