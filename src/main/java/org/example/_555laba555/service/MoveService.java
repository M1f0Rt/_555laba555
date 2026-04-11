package org.example._555laba555.service;

import org.example._555laba555.domain.StockMove;
import org.example._555laba555.validation.MoveValidator;
import org.example._555laba555.validation.ValidationException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Сервис для управления историей движений.
 * Отвечает за добавление, поиск и сортировку движений по партиям.
 * Хранит данные в HashMap
 */
public class MoveService {
    /**
     * Хранилище движений: ключ - ID, значение - объект StockMove
     */
    private Map<Long, StockMove> items = new HashMap<>();

    private long nextId = 1;

    /**
     * Добавляет новое движение в историю.
     * Генерирует ID, проверяет достаточно ли реактива для расхода,
     * устанавливает время создания.
     */
    public StockMove add(StockMove move, double currentQuantity) {
        MoveValidator.validate(move);
        MoveValidator.checkQuantity(move, currentQuantity);

        move.setId(nextId++);
        if (move.getMovedAt() == null) {
            move.setMovedAt(Instant.now());
        }
        move.setCreatedAt(Instant.now());

        items.put(move.getId(), move);
        return move;
    }
    /**
     * Возвращает список движений для указанной партии.
     */
    public List<StockMove> getByBatchId(long batchId) {
        List<StockMove> result = new ArrayList<>();
        for (StockMove m : items.values()) {
            if (m.getBatchId() == batchId) {
                result.add(m);
            }
        }
        return result;
    }
    /**
     * Возвращает список последних движений для указанной партии.
     * Движения сортируются от новых к старым.
     */
    public List<StockMove> getByBatchId(long batchId, int limit) {
        List<StockMove> all = getByBatchId(batchId);
        int n = all.size();
        for (int i = 0; i < n - 1; i++) {
            int maxIdx = i;
            for (int j = i + 1; j < n; j++) {
                if (isNewerThan(all.get(j), all.get(maxIdx))) {
                    maxIdx = j;
                }
            }
            if (maxIdx != i) {
                StockMove temp = all.get(i);
                all.set(i, all.get(maxIdx));
                all.set(maxIdx, temp);
            }
        }

        if (limit < all.size()) {
            return all.subList(0, limit);
        }
        return all;
    }
    public boolean isNewerThan(StockMove a, StockMove b){
        if (a.getMovedAt() == null) return false;
        if (b.getMovedAt() == null) return true;
        return a.getMovedAt().compareTo(b.getMovedAt()) > 0;
    }
    public ArrayList<StockMove> getAll() {
        return new ArrayList<>(items.values());
    }
    public void loadFromList(List<StockMove> list) {
        items.clear();
        for (StockMove m : list) {
            items.put(m.getId(), m);
            if (m.getId() >= nextId) {
                nextId = m.getId() + 1;
            }
        }
    }
}