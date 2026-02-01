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
