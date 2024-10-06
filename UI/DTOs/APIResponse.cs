using System.Net;

namespace UI.DTOs
{
    public sealed class APIResponse<TData>
    {
        public APIResponse(TData data = default!) => Data = data;

        public bool Status { get; set; } = true;
        public IEnumerable<string>? Errors { get; set; }
        public int StatusCode { get; set; } = 200;
        public TData Data { get; set; }

        public static APIResponse<TData> Success(int statusCode, TData data = default!) =>
            new(data)
            {
                StatusCode = statusCode,
                Status = true
            };

        public static APIResponse<TData> Success(TData data = default!, HttpStatusCode statusCode = HttpStatusCode.OK) => Success((int)statusCode, data);

        public static APIResponse<TData> Success(int statusCode) =>
            new()
            {
                StatusCode = statusCode,
                Status = true
            };

        public static APIResponse<TData> Success(HttpStatusCode statusCode = HttpStatusCode.OK) => Success((int)statusCode);

        public static APIResponse<TData> Failure(IEnumerable<string> errors, int statusCode) =>
            new(default!)
            {
                StatusCode = statusCode,
                Status = false,
                Errors = errors
            };
        public static APIResponse<TData> Failure(IEnumerable<string> errors, HttpStatusCode statusCode) => Failure(errors, (int)statusCode);

        public static APIResponse<TData> Failure(string errors, int statusCode) => Failure(new List<string> { errors }, statusCode);
        public static APIResponse<TData> Failure(string errors, HttpStatusCode statusCode) => Failure(new List<string> { errors }, (int)statusCode);
    }
}
