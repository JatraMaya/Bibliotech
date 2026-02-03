import { useState } from 'react';
import { useAuthStore } from '../store/authStore';
import { useAddProfile, useEditProfile } from '../hooks/UseProfile';

export default function EditProfileForm() {
  const user = useAuthStore((state) => state.user);
  const { mutate: mAddProfile } = useAddProfile();
  const { mutate: mEditProfile } = useEditProfile();
  const [avatarFile, setAvatarFile] = useState<File | null>(null);
  let [firstname, lastname] = splitOnce(user?.fullname!);

  const [formData, setFormData] = useState({
    firstname: firstname || '',
    lastname: lastname || '',
    email: user?.email || '',
    bio: user?.bio || '',
  });

  const profileExist = Boolean(user?.bio) || Boolean(user?.avatarUrl);

  function splitOnce(text: string): [string, string] {
    const index = text.indexOf(' ');

    if (index === -1) {
      return [text, ''];
    }

    return [text.slice(0, index), text.slice(index + 1)];
  }

  const handleInputChange = (
    e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>,
  ) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();

    if (!profileExist) {
      mAddProfile({ bio: formData.bio });
    } else {
      console.log('FORM NIH', formData);
      mEditProfile(formData);
    }

    (document.getElementById('my_modal_1') as HTMLDialogElement)?.close();
  };

  return (
    <>
      <form onSubmit={handleSubmit} className="space-y-4">
        {profileExist && (
          <>
            <div className="form-control">
              <label className="floating-label">
                <span className="label-text">Firstname</span>
              </label>
              <input
                type="text"
                name="firstname"
                placeholder="Enter your firstname"
                className="input input-bordered"
                value={formData.firstname}
                onChange={handleInputChange}
                required
              />
            </div>

            <div className="form-control">
              <label className="floating-label">
                <span className="label-text">Lastname</span>
              </label>
              <input
                type="text"
                name="lastname"
                placeholder="Enter your lastname"
                className="input input-bordered"
                value={formData.lastname}
                onChange={handleInputChange}
                required
              />
            </div>

            <div className="form-control">
              <label className="floating-label">
                <span className="label-text">Email</span>
              </label>
              <input
                type="email"
                name="email"
                placeholder="email@example.com"
                className="input input-bordered"
                value={formData.email}
                onChange={handleInputChange}
                required
              />
            </div>
          </>
        )}

        <div className="form-control">
          <label className="floating-label">
            <span className="label-text">Bio</span>
          </label>
          <textarea
            name="bio"
            placeholder="Tell us about yourself"
            className="textarea textarea-bordered"
            value={formData.bio}
            onChange={handleInputChange}
          />
        </div>

        {/* <div className="form-control">
          <label className="floating-label">
            <span className="label-text">Avatar</span>
          </label>
          <input
            type="file"
            accept="image/*"
            className="file-input file-input-bordered w-full"
            onChange={handleFileChange}
          />
        </div> */}

        <div className="form-control">
          <button type="submit" className="btn btn-primary btn-block">
            {profileExist ? 'Save Changes' : 'Add profile'}
          </button>
        </div>

        <div className="form-control">
          <button
            type="button"
            className="btn btn-ghost btn-block"
            onClick={() =>
              (
                document.getElementById('my_modal_1') as HTMLDialogElement
              )?.close()
            }
          >
            Cancel
          </button>
        </div>
      </form>
    </>
  );
}
