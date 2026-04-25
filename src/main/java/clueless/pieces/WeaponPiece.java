package clueless.pieces;

public class WeaponPiece extends Piece {
    public WeaponPiece(String name){ super(PieceType.Weapon, name); }

    public PieceType getType() { return PieceType.Weapon; }

}
