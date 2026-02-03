export function errorToMessage(message: unknown): string {
  if (!message) return 'Something went wrong';

  if (typeof message === 'string') return message;

  if (typeof message === 'object') {
    return Object.values(message as Record<string, unknown>)
      .filter(Boolean)
      .join('\n');
  }

  return 'Unknown error';
}

export function getApiPath(): string {
  return import.meta.env.VITE_API_URL;
}

export function fullnameToInitial(fullname: string) {
  return fullname
    .split(' ')
    .map((word) => word.charAt(0).toUpperCase())
    .join('');
}

export function errorWithStatusCode(
  message: string,
  statusCode: number,
  defaultMessage = '',
): never {
  const err = new Error(message || defaultMessage) as Error & {
    statusCode: number;
  };

  err.statusCode = statusCode;
  throw err;
}
