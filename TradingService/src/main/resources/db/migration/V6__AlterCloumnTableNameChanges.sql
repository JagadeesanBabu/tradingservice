ALTER TABLE jet_option_trading_data
RENAME COLUMN "date" TO "trade_date";

ALTER TABLE jet_option_trading_data
RENAME COLUMN "expiry" TO "expiry_date";

ALTER TABLE jet_option_trading_data
RENAME COLUMN "open" TO "open_price";

ALTER TABLE jet_option_trading_data
RENAME COLUMN "high" TO "high_price";

ALTER TABLE jet_option_trading_data
RENAME COLUMN "low" TO "low_price";

ALTER TABLE jet_option_trading_data
RENAME COLUMN "close" TO "closed_price";

ALTER TABLE jet_option_trading_data
RENAME COLUMN "ltp" TO "last_traded_price";

ALTER TABLE jet_option_trading_data
RENAME COLUMN "open_int" TO "open_interest";

ALTER TABLE jet_option_trading_data
  RENAME TO trade_options_data;

ALTER TABLE trade_options_data
ALTER COLUMN expiry_date TYPE timestamp;

ALTER TABLE trade_options_data
ALTER COLUMN trade_date TYPE timestamp;