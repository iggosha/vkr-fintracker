package ru.golovkov.fintracker.strategy;

public class ForecastStrategyFactory {

    private ForecastStrategyFactory() {
    }

    public static ForecastStrategy getStrategy(String strategyType) {
        return switch (strategyType.toUpperCase()) {
            case "AVG" -> new AverageStrategy();
            case "LIN" -> new LinearRegressionStrategy();
            case "EXP" -> new ExponentialSmoothingStrategy();
            default -> throw new IllegalArgumentException("Неизвестная стратегия прогнозирования: " + strategyType);
        };
    }
}
