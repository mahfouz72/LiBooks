import './ProfileAvatar.css';

function ProfileAvatar({ firstName, lastName, size = 'large' }) {
  return (
    <div className={`profile-avatar ${size}`}>
      <span className="initials">{`${firstName[0]}${lastName[0]}`.toUpperCase()}</span>
    </div>
  );
}

export default ProfileAvatar;