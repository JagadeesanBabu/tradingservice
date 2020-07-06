CREATE TABLE jet_option_trading_data(
   id uuid NOT NULL PRIMARY KEY,
   symbol                   VARCHAR(9) NOT NULL
  ,date                     DATE  NOT NULL
  ,expiry                   DATE  NOT NULL
  ,option_type              VARCHAR(2) NOT NULL
  ,strike_price             NUMERIC(19,0) NOT NULL
  ,open                     NUMERIC(7,2) NOT NULL
  ,high                     NUMERIC(7,2) NOT NULL
  ,low                      NUMERIC(7,2) NOT NULL
  ,close                    NUMERIC(7,2) NOT NULL
  ,ltp                      NUMERIC(7,2) NOT NULL
  ,settle_price             NUMERIC(8,2) NOT NULL
  ,no_of_contracts          INTEGER  NOT NULL
  ,turn_over                NUMERIC(11,2) NOT NULL
  ,premium_turn_over        NUMERIC(8,2) NOT NULL
  ,open_Int                 INTEGER  NOT NULL
  ,change_in_oi             INTEGER  NOT NULL
  ,underlying_value         NUMERIC(19,2) NOT NULL
);